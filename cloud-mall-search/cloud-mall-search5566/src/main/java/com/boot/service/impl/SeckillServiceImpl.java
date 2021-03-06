package com.boot.service.impl;

import com.alibaba.fastjson.JSON;
import com.boot.feign.seckill.fallback.SeckillFallbackFeign;
import com.boot.pojo.*;
import com.boot.service.SeckillSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/** @author 游政杰 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillSearchService {

  @Autowired private SeckillFallbackFeign seckillFallbackFeign;

  @Autowired private RedisTemplate redisTemplate;

  private static final String INDEX_NAME = "cloud-mall-seckill";

  private final String PAGE_SECKILL_TEXT = "pageSeckillText_";

  private final String PAGE_SECKILL_COUNT = "pageSeckillCount_";

  private static final String LOCK = "seckill_search_lock";

  private static final String SECKILL_SEARCH_TEXT="seckill_search_text_";//搜索文本

  private final String SECKILL_NUMBER_KEY="seckill_number_"; //秒杀库存key

  private final String SECKILL_LIMIT_KEY="seckill_limit_";//秒杀限购key

  private final String SECKILL_TIME_KEY="seckill_time_";//秒杀开始时间和结束时间key

  @Autowired private RedissonClient redissonClient;

  @Autowired private RestHighLevelClient restHighLevelClient;

  @Override
  public void initSeckillSearch() throws IOException, InterruptedException {

    RLock lock = redissonClient.getLock(LOCK);

    try {

      if (lock.tryLock(15, 25, TimeUnit.SECONDS)) {

        BulkRequest bulkRequest = new BulkRequest();

        List<Seckill> seckills = seckillFallbackFeign.selectAllSeckill();

        //插入数据
        seckills.forEach((seckill)->{

          IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
          indexRequest.id(seckill.getSeckillId() + ""); // 秒杀商品id
          ConcurrentHashMap<String, Object> sources = new ConcurrentHashMap<>();
          sources.put("seckillName", seckill.getSeckillName());
          sources.put("seckillNumber", seckill.getSeckillNumber());
          sources.put("price", seckill.getPrice());
          sources.put("img", seckill.getImg());
          sources.put("limitCount", String.valueOf(seckill.getLimitCount()));
          sources.put("startTime", seckill.getStartTime());
          sources.put("endTime", seckill.getEndTime());
          sources.put("createTime", seckill.getCreateTime());
          sources.put("userid", String.valueOf(seckill.getUser().getId()));
          indexRequest.source(sources);

          bulkRequest.add(indexRequest);

          //key =SECKILL_NUMBER_KEY加上秒杀商品id，value是库存
          redisTemplate.opsForValue().set(SECKILL_NUMBER_KEY+seckill.getSeckillId(),seckill.getSeckillNumber(),7, TimeUnit.DAYS);

          //key =SECKILL_LIMIT_KEY加上秒杀商品id，value是限购数量
          redisTemplate.opsForValue().set(SECKILL_LIMIT_KEY+seckill.getSeckillId(),seckill.getLimitCount(),7,TimeUnit.DAYS);

          //key =SECKILL_TIME_KEY加上秒杀商品id，value是（开始时间?结束时间）
          String startTime = seckill.getStartTime();
          String endTime = seckill.getEndTime();
          String value=startTime+","+endTime;
          redisTemplate.opsForValue().set(SECKILL_TIME_KEY+seckill.getSeckillId(),value,7,TimeUnit.DAYS);

        });


        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT); // 批量execute
      }

    } finally {

      lock.unlock();
    }
  }

  @Override
  public List<Seckill> searchAllSeckill(String text, int from, int size, String ip)
      throws IOException {

    redisTemplate.opsForValue().set(SECKILL_SEARCH_TEXT+ip,text,3,TimeUnit.DAYS);
    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    // 分页
    searchSourceBuilder.from(from);
    searchSourceBuilder.size(15);

    // *****解决：防止当搜索的文本text查不到数据而导致切换品牌和分类出现错误
    SearchRequest searchRequest1 = new SearchRequest(INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder1 = new SearchSourceBuilder();
    searchSourceBuilder1.query(QueryBuilders.matchQuery("seckillName", text)); // 搜索name
    // 这里就搜索一个数据来判断是否有数据
    searchSourceBuilder1.from(0);
    searchSourceBuilder1.size(1);
    searchRequest1.source(searchSourceBuilder1);
    SearchResponse response = restHighLevelClient.search(searchRequest1, RequestOptions.DEFAULT);

    // 构造布尔查询，进行多条件筛选
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    if (response.getHits().getHits().length <= 0) // 如果不存在,说明输入的text搜索不到数据
    {
      boolQueryBuilder.must(QueryBuilders.matchAllQuery()); // 此时返回全部给前端
    } else {
      boolQueryBuilder.must(QueryBuilders.matchQuery("seckillName", text));
    }

    searchSourceBuilder.query(boolQueryBuilder);

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    SearchHit[] hits = searchResponse.getHits().getHits();

    List<Seckill> seckills = new CopyOnWriteArrayList<>();
    for (SearchHit searchHit : hits) {
      Seckill seckill = new Seckill();
      Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
      BigDecimal price = new BigDecimal(String.valueOf(sourceAsMap.get("price")));
      seckill.setSeckillId(Long.valueOf(searchHit.getId()));
      seckill.setSeckillName((String) sourceAsMap.get("seckillName"));
      seckill.setSeckillNumber((Integer) sourceAsMap.get("seckillNumber"));
      seckill.setPrice(price);
      seckill.setImg((String) sourceAsMap.get("img"));
      seckill.setLimitCount(Integer.valueOf((String) sourceAsMap.get("limitCount")));
      seckill.setStartTime((String) sourceAsMap.get("startTime"));
      seckill.setEndTime((String) sourceAsMap.get("endTime"));
      seckill.setCreateTime((String) sourceAsMap.get("createTime"));
      User user = new User();
      user.setId(Long.valueOf(String.valueOf(sourceAsMap.get("userid"))));
      seckill.setUser(user);
      seckills.add(seckill);
    }
    // 获取分页前查询的总数
    BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
    if (response.getHits().getHits().length <= 0) // 如果不存在,说明输入的text搜索不到数据
    {
      boolQueryBuilder1.must(QueryBuilders.matchAllQuery()); // 此时返回全部给前端
    } else {
      boolQueryBuilder1.must(QueryBuilders.matchQuery("name", text));
    }

    SearchRequest searchRequest2 = new SearchRequest(INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
    // 因为es默认的size为10所以当我们分页搜索全部时需要调大一点size值
    searchSourceBuilder2.from(0);
    searchSourceBuilder2.size(10000);

    searchSourceBuilder2.query(boolQueryBuilder1);
    searchRequest2.source(searchSourceBuilder2);
    SearchResponse searchRespond2 =
        restHighLevelClient.search(searchRequest2, RequestOptions.DEFAULT);

    redisTemplate
        .opsForValue()
        .set(PAGE_SECKILL_COUNT + ip, searchRespond2.getHits().getTotalHits().value);

    return seckills;
  }

  @Override
  public Seckill searchSeckilltoDetailByseckillId(long seckillId) throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    searchSourceBuilder.query(QueryBuilders.termQuery("_id", seckillId));

    searchRequest.source(searchSourceBuilder);

    SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    SearchHit[] hits = search.getHits().getHits();

    Seckill seckill = new Seckill(); // 构造对象
    SearchHit searchHit=  hits[0];
    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
    BigDecimal price = new BigDecimal(String.valueOf(sourceAsMap.get("price")));
    seckill.setSeckillName((String) sourceAsMap.get("seckillName"));
    seckill.setSeckillNumber((Integer) sourceAsMap.get("seckillNumber"));
    seckill.setPrice(price);
    seckill.setImg((String) sourceAsMap.get("img"));
    seckill.setStartTime((String) sourceAsMap.get("startTime"));
    seckill.setEndTime((String) sourceAsMap.get("endTime"));
    seckill.setUser(null);
    return seckill;
  }

  @Override
  public void addSeckillToElasticSearchAndRedis(Seckill seckill) throws IOException {


    IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
    indexRequest.id(seckill.getSeckillId() + ""); // 秒杀商品id
    ConcurrentHashMap<String, Object> sources = new ConcurrentHashMap<>();
    sources.put("seckillName", seckill.getSeckillName());
    sources.put("seckillNumber", seckill.getSeckillNumber());
    sources.put("price", seckill.getPrice());
    sources.put("img", seckill.getImg());
    sources.put("limitCount", String.valueOf(seckill.getLimitCount()));
    sources.put("startTime", seckill.getStartTime());
    sources.put("endTime", seckill.getEndTime());
    sources.put("createTime", seckill.getCreateTime());
    sources.put("userid", String.valueOf(seckill.getUser().getId()));
    indexRequest.source(sources);

    restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);

    //key =SECKILL_NUMBER_KEY加上秒杀商品id，value是库存
    redisTemplate.opsForValue().set(SECKILL_NUMBER_KEY+seckill.getSeckillId(),seckill.getSeckillNumber(),7, TimeUnit.DAYS);

    //key =SECKILL_LIMIT_KEY加上秒杀商品id，value是限购数量
    redisTemplate.opsForValue().set(SECKILL_LIMIT_KEY+seckill.getSeckillId(),seckill.getLimitCount(),7,TimeUnit.DAYS);

    //key =SECKILL_TIME_KEY加上秒杀商品id，value是（开始时间?结束时间）
    String startTime = seckill.getStartTime();
    String endTime = seckill.getEndTime();
    String value=startTime+","+endTime;
    redisTemplate.opsForValue().set(SECKILL_TIME_KEY+seckill.getSeckillId(),value,7,TimeUnit.DAYS);


  }

  @Override
  public void updateSeckill(Seckill seckill) throws IOException {
    UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME,seckill.getSeckillId()+"");

    ConcurrentHashMap<String, Object> doc = new ConcurrentHashMap<>();
    doc.put("seckillName", seckill.getSeckillName());
    doc.put("seckillNumber", seckill.getSeckillNumber());
    doc.put("price", seckill.getPrice());
    if(!StringUtils.isEmpty(seckill.getImg())){
      doc.put("img", seckill.getImg());
    }
    doc.put("limitCount", String.valueOf(seckill.getLimitCount()));
    doc.put("startTime", seckill.getStartTime());
    doc.put("endTime", seckill.getEndTime());

    updateRequest.doc(JSON.toJSONString(doc), XContentType.JSON);

    restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
  }

  @Override
  public void deleteSeckill(long seckillId) throws IOException {
    DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME,seckillId+"");

    restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
  }

  @Override
  public void batchDeleteSeckill(long[] ids) throws IOException {
    BulkRequest bulkRequest = new BulkRequest(); //批量执行
    for (long id : ids) {
      DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME);
      deleteRequest.id(id+"");
      bulkRequest.add(deleteRequest);
    }
    restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
  }
}

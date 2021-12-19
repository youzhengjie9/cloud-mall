package com.boot.service.impl;

import com.alibaba.fastjson.JSON;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Classify;
import com.boot.pojo.Product;
import com.boot.service.SearchService;
import com.boot.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author 游政杰
 * @date 2021/11/9 21:43
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

  private final String INDEX_NAME = "cloud-mall"; // 索引名

  private final String PAGE_PRODUCT_COUNT = "pageProductCount_"; // 分页之前查询的总数

  @Autowired private RestHighLevelClient restHighLevelClient;

  @Autowired private ProductFallbackFeign productFallbackFeign;

  @Autowired private RedisTemplate redisTemplate;

  @Autowired
  private SearchService searchService;

  private static final String LOCK="search_lock";

  @Autowired
  private RedissonClient redissonClient;

  @Override
  public void initSearch() throws IOException, InterruptedException {

    RLock lock = redissonClient.getLock(LOCK);

   try{
     if(lock.tryLock(15,25, TimeUnit.SECONDS))
     {
       BulkRequest bulkRequest = new BulkRequest();

       List<Product> products = productFallbackFeign.selectAllProduct();
       // 插入数据
       for (Product product : products) {
         IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
         indexRequest.id(product.getProductId() + ""); // 商品id

         ConcurrentHashMap<String, Object> sources = new ConcurrentHashMap<>();
         sources.put("name", product.getName());
         sources.put("price", product.getPrice());
         sources.put("img", product.getImg());
         sources.put("number", product.getNumber());
         sources.put("fl_id", String.valueOf(product.getClassify().getId()));
         sources.put("b_id", String.valueOf(product.getBrand().getId()));
         sources.put("fl_name", String.valueOf(product.getClassify().getText()));
         sources.put("b_name", String.valueOf(product.getBrand().getBrandName()));
         sources.put("introduce_img", product.getIntroduce_img());
         indexRequest.source(sources);

         bulkRequest.add(indexRequest);
       }

       restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT); // 批量execute
     }

   }
   finally{

     lock.unlock();
   }
  }

  @Override
  public SearchHit[] searchProductHitByName(String text,String ip) throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder builder = new SearchSourceBuilder();

    builder.from(0);
    builder.size(15);

    BoolQueryBuilder queryBuilder =
        QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", text));
    builder.query(queryBuilder);

    searchRequest.source(builder);

    SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    return search.getHits().getHits();
  }

  @Override
  public List<Product> searchProductByHit(String text, SearchHit[] searchHits,String ip) throws IOException {

    List<Product> products = new CopyOnWriteArrayList<>(); // 存储搜索来的products
    if (searchHits != null && searchHits.length > 0 && !text.equals("^")) {

      for (SearchHit searchHit : searchHits) {
        Product product = new Product();
        Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

        BigDecimal price = new BigDecimal(Double.toString((Double) sourceAsMap.get("price")));
        product.setProductId(Long.valueOf(searchHit.getId()));
        product.setName((String) sourceAsMap.get("name"));
        product.setPrice(price);
        product.setImg((String) sourceAsMap.get("img"));
        product.setNumber((Integer) sourceAsMap.get("number"));
        Classify classify = new Classify();
        classify.setId(Long.valueOf((String) sourceAsMap.get("fl_id")));
        classify.setText((String)sourceAsMap.get("fl_name"));
        product.setClassify(classify);
        Brand brand = new Brand();
        brand.setId(Long.valueOf((String) sourceAsMap.get("b_id")));
        brand.setBrandName((String)sourceAsMap.get("b_name"));
        product.setBrand(brand);
        product.setIntroduce_img((String) sourceAsMap.get("introduce_img"));

        products.add(product);
      }

      // 获取分页前查询的总数
      BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
      boolQueryBuilder1.must(QueryBuilders.matchQuery("name", text));

      SearchRequest searchRequest2 = new SearchRequest(INDEX_NAME);
      SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
      searchSourceBuilder2.query(boolQueryBuilder1);
      searchRequest2.source(searchSourceBuilder2);
      SearchResponse searchRespond2 =
          restHighLevelClient.search(searchRequest2, RequestOptions.DEFAULT);



      redisTemplate
          .opsForValue()
          .set(PAGE_PRODUCT_COUNT+ip, searchRespond2.getHits().getTotalHits().value);

    } else { // 默认搜索全部数据并分页

      // 获取分页前查询的总数
      BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
      boolQueryBuilder1.must(QueryBuilders.matchAllQuery());
      SearchRequest searchRequest2 = new SearchRequest(INDEX_NAME);
      SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();

      searchSourceBuilder2.from(0);
      searchSourceBuilder2.size(15);
      searchSourceBuilder2.query(boolQueryBuilder1);
      searchRequest2.source(searchSourceBuilder2);
      SearchResponse searchRespond2 =
          restHighLevelClient.search(searchRequest2, RequestOptions.DEFAULT);

      SearchHit[] hits = searchRespond2.getHits().getHits();

      for (SearchHit searchHit : hits) {
        Product product = new Product();
        Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
        BigDecimal price = new BigDecimal(Double.toString((Double) sourceAsMap.get("price")));
        product.setProductId(Long.valueOf(searchHit.getId()));
        product.setName((String) sourceAsMap.get("name"));
        product.setPrice(price);
        product.setImg((String) sourceAsMap.get("img"));
        product.setNumber((Integer) sourceAsMap.get("number"));
        Classify classify = new Classify();
        classify.setId(Long.valueOf((String) sourceAsMap.get("fl_id")));
        classify.setText((String)sourceAsMap.get("fl_name"));
        product.setClassify(classify);
        Brand brand = new Brand();
        brand.setId(Long.valueOf((String) sourceAsMap.get("b_id")));
        brand.setBrandName((String) sourceAsMap.get("b_name"));
        product.setBrand(brand);
        product.setIntroduce_img((String) sourceAsMap.get("introduce_img"));

        products.add(product);
      }

      // 获取分页前查询的总数
      BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
      boolQueryBuilder2.must(QueryBuilders.matchAllQuery());

      SearchRequest searchRequest3 = new SearchRequest(INDEX_NAME);
      SearchSourceBuilder searchSourceBuilder3 = new SearchSourceBuilder();
      searchSourceBuilder3.query(boolQueryBuilder2);
      searchRequest3.source(searchSourceBuilder3);
      SearchResponse searchRespond3 =
          restHighLevelClient.search(searchRequest3, RequestOptions.DEFAULT);



      redisTemplate
          .opsForValue()
          .set(PAGE_PRODUCT_COUNT+ip, searchRespond3.getHits().getTotalHits().value);
    }

    return products;
  }

  @Override
  public List<Product> searchAllProductByLimit(int from, int size) throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    searchSourceBuilder.query();
    // 设置分页
    searchSourceBuilder.from(from);
    searchSourceBuilder.size(size);

    searchRequest.source(searchSourceBuilder);

    SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    SearchHit[] hits = search.getHits().getHits();

    List<Product> products = new CopyOnWriteArrayList<>();
    for (SearchHit searchHit : hits) {
      Product product = new Product();
      Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
      BigDecimal price = new BigDecimal(Double.toString((Double) sourceAsMap.get("price")));
      product.setProductId(Long.valueOf(searchHit.getId()));
      product.setName((String) sourceAsMap.get("name"));
      product.setPrice(price);
      product.setImg((String) sourceAsMap.get("img"));
      product.setNumber((Integer) sourceAsMap.get("number"));
      Classify classify = new Classify();
      classify.setId(Long.valueOf((String) sourceAsMap.get("fl_id")));
      classify.setText((String)sourceAsMap.get("fl_name"));
      product.setClassify(classify);
      Brand brand = new Brand();
      brand.setId(Long.valueOf((String) sourceAsMap.get("b_id")));
      brand.setBrandName((String)sourceAsMap.get("b_name"));
      product.setBrand(brand);
      product.setIntroduce_img((String) sourceAsMap.get("introduce_img"));

      products.add(product);
    }
    return products;
  }

  @Override
  public List<Product> searchProductsByCondition(
      String text, long brandid, long classifyid, int from, int size,String ip) throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    //分页
    searchSourceBuilder.from(from);
    searchSourceBuilder.size(15);

    // *****解决：防止当搜索的文本text查不到数据而导致切换品牌和分类出现错误
    SearchRequest searchRequest1 = new SearchRequest(INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder1 = new SearchSourceBuilder();
    searchSourceBuilder1.query(QueryBuilders.matchQuery("name", text)); // 搜索name
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
      boolQueryBuilder.must(QueryBuilders.matchQuery("name", text));
    }

    if (brandid != 0) {
      boolQueryBuilder.must(QueryBuilders.termQuery("b_id", String.valueOf(brandid)));
    }
    if (classifyid != 0) {
      boolQueryBuilder.must(QueryBuilders.termQuery("fl_id", String.valueOf(classifyid)));
    }

    searchSourceBuilder.query(boolQueryBuilder);

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    SearchHit[] hits = searchResponse.getHits().getHits();

    List<Product> products = new CopyOnWriteArrayList<>();
    for (SearchHit searchHit : hits) {
      Product product = new Product();
      Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
      BigDecimal price = new BigDecimal(Double.toString((Double) sourceAsMap.get("price")));
      product.setProductId(Long.valueOf(searchHit.getId()));
      product.setName((String) sourceAsMap.get("name"));
      product.setPrice(price);
      product.setImg((String) sourceAsMap.get("img"));
      product.setNumber((Integer) sourceAsMap.get("number"));
      Classify classify = new Classify();
      classify.setId(Long.valueOf((String) sourceAsMap.get("fl_id")));
      classify.setText((String)sourceAsMap.get("fl_name"));
      product.setClassify(classify);
      Brand brand = new Brand();
      brand.setId(Long.valueOf((String) sourceAsMap.get("b_id")));
      brand.setBrandName((String)sourceAsMap.get("b_name"));
      product.setBrand(brand);
      product.setIntroduce_img((String) sourceAsMap.get("introduce_img"));
      products.add(product);
    }

    // 获取分页前查询的总数

    BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
    if (response.getHits().getHits().length <= 0) // 如果不存在,说明输入的text搜索不到数据
    {
      boolQueryBuilder1.must(QueryBuilders.matchAllQuery()); // 此时返回全部给前端
    } else {
      boolQueryBuilder1.must(QueryBuilders.matchQuery("name", text));
    }

    if (brandid != 0) {
      boolQueryBuilder1.must(QueryBuilders.termQuery("b_id", String.valueOf(brandid)));
    }
    if (classifyid != 0) {
      boolQueryBuilder1.must(QueryBuilders.termQuery("fl_id", String.valueOf(classifyid)));
    }

    SearchRequest searchRequest2 = new SearchRequest(INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
    //因为es默认的size为10所以当我们分页搜索全部时需要调大一点size值
    searchSourceBuilder2.from(0);
    searchSourceBuilder2.size(10000);

    searchSourceBuilder2.query(boolQueryBuilder1);
    searchRequest2.source(searchSourceBuilder2);
    SearchResponse searchRespond2 =
            restHighLevelClient.search(searchRequest2, RequestOptions.DEFAULT);

    redisTemplate
            .opsForValue()
            .set(PAGE_PRODUCT_COUNT+ip, searchRespond2.getHits().getTotalHits().value);


    return products;
  }

  @Override
  public long searchAllProductsCount() throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    searchRequest.source(query);
    SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    TotalHits totalHits = searchResponse.getHits().getTotalHits();

    return totalHits.value;
  }

  @Override
  public List<Product> searchProductsByNameAndLimit(int from, int size, String text) throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    searchSourceBuilder.from(from);
    searchSourceBuilder.size(size);


    searchSourceBuilder.query(QueryBuilders.matchQuery("name",text));
    searchRequest.source(searchSourceBuilder);

    SearchResponse response = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

    SearchHit[] hits = response.getHits().getHits();

    CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<>();

    for (SearchHit searchHit : hits) {
      Product product = new Product();
      Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

      BigDecimal price = new BigDecimal(Double.toString((Double) sourceAsMap.get("price")));
      product.setProductId(Long.valueOf(searchHit.getId()));
      product.setName((String) sourceAsMap.get("name"));
      product.setPrice(price);
      product.setImg((String) sourceAsMap.get("img"));
      product.setNumber((Integer) sourceAsMap.get("number"));
      Classify classify = new Classify();
      classify.setId(Long.valueOf((String) sourceAsMap.get("fl_id")));
      classify.setText((String)sourceAsMap.get("fl_name"));
      product.setClassify(classify);
      Brand brand = new Brand();
      brand.setId(Long.valueOf((String) sourceAsMap.get("b_id")));
      brand.setBrandName((String)sourceAsMap.get("b_name"));
      product.setBrand(brand);
      product.setIntroduce_img((String) sourceAsMap.get("introduce_img"));

      products.add(product);
    }

    return products;
  }

  @Override
  public long searchProductCountByName(String text) throws IOException {

    SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    searchSourceBuilder.query(QueryBuilders.matchQuery("name",text));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    TotalHits totalHits = searchResponse.getHits().getTotalHits();

    return totalHits.value;
  }

  @Override
  public void updateProduct(Product product) throws IOException {

    UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME,product.getProductId()+"");

    Map<String, Object> doc = new HashMap<>();
    doc.put("name", product.getName());
    doc.put("price", product.getPrice());
    doc.put("img", product.getImg());
    doc.put("number", product.getNumber());
    doc.put("fl_id", String.valueOf(product.getClassify().getId()));
    doc.put("b_id", String.valueOf(product.getBrand().getId()));
    doc.put("fl_name", String.valueOf(product.getClassify().getText()));
    doc.put("b_name", String.valueOf(product.getBrand().getBrandName()));
    doc.put("introduce_img", product.getIntroduce_img());

    updateRequest.doc(JSON.toJSONString(doc), XContentType.JSON);

    restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);

  }

  @Override
  public void deleteProduct(long productid) throws IOException {

    DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME,productid+"");

    restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);

  }

  @Override
  public void batchDeleteProcts(long[] ids) throws IOException {

    BulkRequest bulkRequest = new BulkRequest(); //批量执行
    for (long id : ids) {
      DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME);
      deleteRequest.id(id+"");
      bulkRequest.add(deleteRequest);
    }
    restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);

  }

}

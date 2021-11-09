package com.boot.service.impl;

import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.pojo.Product;
import com.boot.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 游政杰
 * @date 2021/11/9 21:43
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final String INDEX_NAME="cloud-mall"; //索引名
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    private static final Object lock=new Object(); //lock1

    @Override
    public void initSearch() throws IOException {

        synchronized (lock)
        {
            BulkRequest bulkRequest = new BulkRequest();

            List<Product> products = productFallbackFeign.selectAllProduct();
            //插入数据
            for (Product product : products) {
                IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
                indexRequest.id(product.getProductId()+""); //商品id

                ConcurrentHashMap<String, Object> sources = new ConcurrentHashMap<>();
                sources.put("name",product.getName());
                sources.put("price",product.getPrice());
                sources.put("img",product.getImg());
                sources.put("number",product.getNumber());
                sources.put("fl_id",String.valueOf(product.getFl_id()));
                sources.put("b_id",String.valueOf(product.getB_id()));
                sources.put("introduce_img",product.getIntroduce_img());
                indexRequest.source(sources);
                bulkRequest.add(indexRequest);
            }

            restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT); //批量execute

        }

    }

    @Override
    public SearchHit[] searchProductHitByName(String text) throws IOException {

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder builder=new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder =
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", text));
        builder.query(queryBuilder);

        searchRequest.source(builder);

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return search.getHits().getHits();
    }

    @Override
    public List<Product> searchProductByHit(SearchHit[] searchHits) {

        List<Product> products = new CopyOnWriteArrayList<>(); //存储搜索来的products
        for (SearchHit searchHit : searchHits) {
            Product product = new Product();
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

            product.setProductId(Long.valueOf(searchHit.getId()));
            product.setName((String) sourceAsMap.get("name"));
            product.setPrice((Double) sourceAsMap.get("price"));
            product.setImg((String) sourceAsMap.get("img"));
            product.setNumber((Integer) sourceAsMap.get("number"));
            product.setFl_id(Long.valueOf((String) sourceAsMap.get("fl_id")));
            product.setB_id(Long.valueOf((String) sourceAsMap.get("b_id")));
            product.setIntroduce_img((String) sourceAsMap.get("introduce_img"));

            products.add(product);

        }

        return products;
    }
}

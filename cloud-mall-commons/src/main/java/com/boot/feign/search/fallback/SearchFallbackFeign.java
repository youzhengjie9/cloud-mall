package com.boot.feign.search.fallback;

import com.boot.feign.search.fallback.impl.SearchFallbackFeignImpl;
import com.boot.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author 游政杰
 */
@FeignClient(value = "cloud-mall-search",fallback = SearchFallbackFeignImpl.class)
@Component
public interface SearchFallbackFeign {

    //搜索服务初始化
    @ResponseBody
    @GetMapping(path = "/feign/search/initSearch")
    public String initSearch() throws IOException;


    //根据name进行查询
    @ResponseBody
    @GetMapping(path = "/feign/search/searchProductByName/{text}")
    public List<Product> searchProductByName(@PathVariable("text") String text) throws IOException;


}

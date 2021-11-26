package com.boot.feign.search.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.search.fallback.impl.SearchFallbackFeignImpl;
import com.boot.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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


    //查询所有数据并分页
    @ResponseBody
    @GetMapping(path = "/feign/search/searchAllProductByLimit/{from}/{size}")
    public List<Product> searchAllProductByLimit(@PathVariable("from") int from,
                                                 @PathVariable("size") int size) throws IOException;

    //from size 为分页
    @ResponseBody
    @GetMapping(path = "/feign/search/searchProductsByCondition")
    public List<Product> searchProductsByCondition(@RequestParam("text") String text,
                                                   @RequestParam("brandid") long brandid,
                                                   @RequestParam("classifyid")long classifyid,
                                                   @RequestParam("from")int from,
                                                   @RequestParam("size")int size) throws IOException;

    @ResponseBody
    @GetMapping(path = "/feign/search/searchAllProductsCount")
    public CommonResult<Long> searchAllProductsCount();


    //根据商品名搜索商品集合并且分页
    @ResponseBody
    @GetMapping(path = "/feign/search/searchProductsByNameAndLimit/{from}/{size}/{text}")
    public CommonResult<List<Product>> searchProductsByNameAndLimit(@PathVariable("from") int from ,
                                                                    @PathVariable("size") int size ,
                                                                    @PathVariable("text") String text) throws IOException;

    //根据商品名搜索商品的目标数
    @ResponseBody
    @GetMapping(path = "/feign/search/searchProductCountByName/{text}")
    public CommonResult<Long> searchProductCountByName(@PathVariable("text") String text) throws IOException;


    @ResponseBody
    @GetMapping(path = "/feign/search/updateProduct")
    public CommonResult<String> updateProduct(@RequestBody Product product) throws IOException;


    @ResponseBody
    @GetMapping(path = "/feign/search/deleteProduct/{productId}")
    public CommonResult<String> deleteProduct(@PathVariable("productId") long productId);

    @ResponseBody
    @GetMapping(path = "/feign/search/batchDeleteProcts")
    public CommonResult<String> batchDeleteProcts (@RequestParam("ids")long[] ids) throws IOException;



}

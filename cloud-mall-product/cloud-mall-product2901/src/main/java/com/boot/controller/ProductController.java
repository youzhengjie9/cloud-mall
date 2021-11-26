package com.boot.controller;

import com.boot.data.CommonResult;
import com.boot.pojo.Product;
import com.boot.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/feign/product")
@Api("商品服务api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping(path = "/selectAllProduct")
    public List<Product> selectAllProduct()
    {
        return productService.selectAllProduct();
    }


    @ResponseBody
    @GetMapping(path = "/selectIntroduceByPid/{pid}")
    public String[] selectIntroduceByPid(@PathVariable("pid") long pid)
    {
        String imgs = productService.selectIntroduceByPid(pid);
        String[] imgarr = imgs.split(",");
        return imgarr;
    }
    @ResponseBody
    @GetMapping(path = "/selectProductByPid/{productId}")
    public Product selectProductByPid(@PathVariable("productId") long productId){

        Product product = productService.selectProductByPid(productId);
        return product;
    }

    //减库存
    @ResponseBody
    @GetMapping(path = "/decrNumberByPid/{productId}/{number}")
    public CommonResult<Product> decrNumberByPid(@PathVariable("productId") long productId,
                                                   @PathVariable("number") int number){

        CommonResult<Product> commonResult = new CommonResult<>();
        productService.decrNumberByPid(productId, number);

        return commonResult;
    }


    //查询所有商品数量
    @ResponseBody
    @GetMapping(path = "/selectProductCount")
    public int selectProductCount(){

        return productService.selectProductCount();
    }

    //添加商品
    @ResponseBody
    @PostMapping(path = "/insertProduct")
    public CommonResult<Product> insertProduct(@RequestBody Product product){
        CommonResult<Product> commonResult = new CommonResult<>();

        productService.insertProduct(product);

        return commonResult;

    }
    @ResponseBody
    @PostMapping(path = "/updateProduct")
    public CommonResult<Product> updateProduct(@RequestBody Product product)
    {
        CommonResult<Product> commonResult = new CommonResult<>();

        productService.updateProduct(product);

        return commonResult;
    }

    @ResponseBody
    @GetMapping(path = "/deleteProduct/{productId}")
    public CommonResult<String> deleteProduct(@PathVariable("productId") long productId){
        CommonResult<String> commonResult = new CommonResult<>();
        productService.deleteProduct(productId);
        return commonResult;
    }

    @ResponseBody
    @GetMapping(path = "/batchDeleteProducts")
    public CommonResult<String> batchDeleteProducts(@RequestParam("ids")long[] ids){

        CommonResult<String> commonResult = new CommonResult<>();
        productService.batchDeleteProducts(ids);
        return commonResult;
    }


}

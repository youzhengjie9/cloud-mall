package com.boot.controller;

import com.boot.pojo.Product;
import com.boot.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}

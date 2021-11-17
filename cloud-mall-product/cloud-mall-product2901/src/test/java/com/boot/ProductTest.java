package com.boot;

import com.boot.pojo.Product;
import com.boot.service.ProductService;
import com.boot.utils.SnowId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication2901.class)
public class ProductTest {

    @Autowired
    private ProductService productService;

    //一键插入product数据
    @Test
    public void generateProducts()
    {
        int count=0;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 80; i++) {
            long productid = SnowId.nextId(); //生成id
            String name="测试产品"+i;
            BigDecimal bigDecimal = new BigDecimal(Double.toString(16.3*i)); //要把数值转换成String
            int num=2*i;
            Product product = new Product();
            product.setProductId(productid);
            product.setName(name);
            product.setPrice(bigDecimal);
            product.setImg("mipad2-64");
            product.setNumber(num);
            product.setFl_id(2);
            product.setB_id(3);
            product.setIntroduce_img("/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg");

            productService.insertProduct(product);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++count;
      System.out.println("已生成"+count+"个数据");
        }
        long end = System.currentTimeMillis();
    System.out.println("共耗时："+(end-start)+"ms");
    }





}


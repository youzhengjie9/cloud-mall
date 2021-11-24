package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiJSON;
import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.feign.product.fallback.ClassifyFallbackFeign;
import com.boot.feign.product.notFallback.ProductFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Classify;
import com.boot.pojo.Product;
import com.boot.utils.FileUtil;
import com.boot.utils.IpUtils;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Api("后台商品控制器")
@Controller
@RequestMapping(path = "/pear")
@Slf4j
public class ProductController {

    private final String INDEX_NAME = "cloud-mall"; // 索引名

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private BrandFallbackFeign brandFallbackFeign;

    @Autowired
    private ClassifyFallbackFeign classifyFallbackFeign;

    @Autowired
    private UserFallbackFeign userFallbackFeign;


    //发布商品页面
    @Operation("进入发布商品界面")
    @RequestMapping(path = "/topublish")
    public String toPublishProduct(Model model, HttpSession session, HttpServletRequest request) {

        List<Brand> brandList = brandFallbackFeign.selectAllBrand(); //查询所有品牌

        List<Classify> classifyList = classifyFallbackFeign.selectAllClassify(); //查询所有分类

        model.addAttribute("brandList",brandList);
        model.addAttribute("classifyList",classifyList);

        String username = springSecurityUtil.currentUser(session);
        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        String ipAddr = IpUtils.getIpAddr(request);
        log.debug(time + "   用户名：" + username + "进入后台发布页面：ip为" + ipAddr);

        model.addAttribute("ps", "发布商品");
        model.addAttribute("url", "/product/publish");
        return "back/product_edit";
    }

    @Operation("发布商品")
    @RequestMapping(path = "/product/publish")
    @ResponseBody
    public String publish(Product product, String brandid, String classifyid, String content, MultipartFile file,
            HttpSession session, HttpServletRequest request) throws FileNotFoundException {

        layuiJSON json = new layuiJSON(); //封装json数据传入前台

        try {
            //后台对前台传入数据进行校验

            //图片校验
            if(file.isEmpty()){
                throw new RuntimeException("图片为空");
            }
            if(StringUtils.isBlank(content))
            {
                throw new RuntimeException("商品简介内容为空");
            }
            long bid = Long.parseLong(brandid);
            long cid = Long.parseLong(classifyid);
            if(bid<=0||cid<=0)
            {
                throw new RuntimeException("请选择品牌分类");
            }

            product.setProductId(SnowId.nextId());
            product.setFl_id(cid);
            product.setB_id(bid);
            product.setContent(content);
            product.setIntroduce_img("/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg");
            String currentUser = springSecurityUtil.currentUser(session);
            long userid = userFallbackFeign.selectUserIdByName(currentUser);
            product.setUserid(userid);
            String img = FileUtil.writeImage(file.getOriginalFilename(), file.getBytes());
            product.setImg(img);
            //发布操作代码
            productFeign.insertProduct(product);


            //发布成功后加入到es中
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
            indexRequest.id(product.getProductId() + ""); // 商品id

            ConcurrentHashMap<String, Object> sources = new ConcurrentHashMap<>();
            sources.put("name", product.getName());
            sources.put("price", product.getPrice());
            sources.put("img", product.getImg());
            sources.put("number", product.getNumber());
            sources.put("fl_id", String.valueOf(product.getFl_id()));
            sources.put("b_id", String.valueOf(product.getB_id()));
            sources.put("introduce_img", product.getIntroduce_img());
            indexRequest.source(sources);

            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            //打印日志操作
            String username = springSecurityUtil.currentUser(session);
            java.util.Date date2 = new java.util.Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(date2);
            String ipAddr = IpUtils.getIpAddr(request);
            log.debug(time + "   用户名：" + username + "发布成功,ip为：" + ipAddr);
            json.setSuccess(true);
            json.setMsg("发布成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("发布失败");
        }
        return JSON.toJSONString(json);
    }


}

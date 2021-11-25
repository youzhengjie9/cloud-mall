package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.annotation.Operation;
import com.boot.annotation.Visitor;
import com.boot.data.CommonResult;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.feign.product.fallback.ClassifyFallbackFeign;
import com.boot.feign.product.notFallback.ProductFeign;
import com.boot.feign.search.fallback.SearchFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Classify;
import com.boot.pojo.Product;
import com.boot.utils.FileUtil;
import com.boot.utils.IpUtils;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author 游政杰 */
@Api("后台商品控制器")
@Controller
@RequestMapping(path = "/pear")
@Slf4j
public class ProductController {

  private final String INDEX_NAME = "cloud-mall"; // 索引名

  @Autowired private RestHighLevelClient restHighLevelClient;

  @Autowired private SpringSecurityUtil springSecurityUtil;

  @Autowired private ProductFeign productFeign;

  @Autowired private BrandFallbackFeign brandFallbackFeign;

  @Autowired private ClassifyFallbackFeign classifyFallbackFeign;

  @Autowired private UserFallbackFeign userFallbackFeign;

  @Autowired private SearchFallbackFeign searchFallbackFeign;

  // 发布商品页面
  @Operation("进入发布商品界面")
  @RequestMapping(path = "/topublish")
  public String toPublishProduct(Model model, HttpSession session, HttpServletRequest request) {

    List<Brand> brandList = brandFallbackFeign.selectAllBrand(); // 查询所有品牌

    List<Classify> classifyList = classifyFallbackFeign.selectAllClassify(); // 查询所有分类

    model.addAttribute("brandList", brandList);
    model.addAttribute("classifyList", classifyList);

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
  public String publish(
      Product product,
      String brandid,
      String classifyid,
      String content,
      MultipartFile file,
      HttpSession session,
      HttpServletRequest request)
      throws FileNotFoundException {

    layuiJSON json = new layuiJSON(); // 封装json数据传入前台

    try {
      // 后台对前台传入数据进行校验

      // 图片校验
      if (file.isEmpty()) {
        throw new RuntimeException("图片为空");
      }
      if (StringUtils.isBlank(content)) {
        throw new RuntimeException("商品简介内容为空");
      }
      long bid = Long.parseLong(brandid);
      long cid = Long.parseLong(classifyid);
      if (bid <= 0 || cid <= 0) {
        throw new RuntimeException("请选择品牌分类");
      }

      product.setProductId(SnowId.nextId());
      Classify classify = new Classify();
      classify.setId(cid);
      product.setClassify(classify);
      Brand brand = new Brand();
      brand.setId(bid);
      product.setBrand(brand);
      product.setContent(content);
      product.setIntroduce_img(
          "/static/img/introduce/04e6887165a1d0b7.jpg,/static/img/introduce/05b406882a48787a.jpg,/static/img/introduce/528c61caf149031e.jpg,/static/img/introduce/62313e3bdbbeaf29.jpg");
      String currentUser = springSecurityUtil.currentUser(session);
      long userid = userFallbackFeign.selectUserIdByName(currentUser);
      product.setUserid(userid);
      String img = FileUtil.writeImage(file.getOriginalFilename(), file.getBytes());
      product.setImg(img);
      // 发布操作代码
      productFeign.insertProduct(product);

      // 发布成功后加入到es中
      IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
      indexRequest.id(product.getProductId() + ""); // 商品id

      ConcurrentHashMap<String, Object> sources = new ConcurrentHashMap<>();
      sources.put("name", product.getName());
      sources.put("price", product.getPrice());
      sources.put("img", product.getImg());
      sources.put("number", product.getNumber());
      sources.put("fl_id", String.valueOf(product.getClassify().getId()));
      sources.put("b_id", String.valueOf(product.getBrand().getId()));
      sources.put("introduce_img", product.getIntroduce_img());
      indexRequest.source(sources);

      restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

      // 打印日志操作
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

  // 商品管理
  @Operation("进入商品列表界面")
  @Visitor(desc = "进入商品列表界面")
  @RequestMapping(path = "/toProductManager")
  @ApiOperation(value = "进入商品列表界面", notes = "进入商品列表界面，分页默认是第一页")
  public String toProductManager(Model model, HttpSession session, HttpServletRequest request)
      throws IOException {

    String username = springSecurityUtil.currentUser(session);
    java.util.Date date = new java.util.Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = simpleDateFormat.format(date);
    String ipAddr = IpUtils.getIpAddr(request);
    log.debug(time + "   用户名：" + username + "查看列表,ip为：" + ipAddr);

    List<Product> products = searchFallbackFeign.searchAllProductByLimit(0, 6);

    model.addAttribute("products", products);
    return "back/product_list";
  }

  @ResponseBody
  @RequestMapping("/productdata")
  public String articleData(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "6") int limit,
      @RequestParam(value = "title", defaultValue = "") String title)
      throws IOException {
    // page=1     page=2
    // page:  limit*0 -- limit *1

    page = limit * (page - 1);
    /** 两种情况：1：当我们不是点击查询按钮时，则展示全部 2.点击查询按钮则展示查到的数据 */
    if (StringUtils.isNotBlank(title)) { // 查询
      CommonResult<Long> longCommonResult = searchFallbackFeign.searchProductCountByName(title);
      Long total = longCommonResult.getObj();

      CommonResult<List<Product>> result1 = searchFallbackFeign.searchProductsByNameAndLimit(page, limit, title);
      List<Product> products = result1.getObj();

      layuiData<Product> productlayuiData = new layuiData<>();
      productlayuiData.setMsg("");
      productlayuiData.setCode(0);
      productlayuiData.setCount(Integer.parseInt(String.valueOf(total)));
      productlayuiData.setData(products);
      return JSON.toJSONString(productlayuiData);
    } else {

      CommonResult<Long> longCommonResult = searchFallbackFeign.searchAllProductsCount();

      int total = Integer.parseInt(String.valueOf(longCommonResult.getObj()));

      List<Product> products = searchFallbackFeign.searchAllProductByLimit(page, limit);

      layuiData<Product> layuiArticleData = new layuiData<Product>();
      layuiArticleData.setCode(0);
      layuiArticleData.setMsg("");
      layuiArticleData.setCount(total); // “”总共“”的记录数
      layuiArticleData.setData(products); // “”分页“”后的数据
      return JSON.toJSONString(layuiArticleData);
    }
  }
}

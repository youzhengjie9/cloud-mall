package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiJSON;
import com.boot.pojo.Product;
import com.boot.utils.IpUtils;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
import java.text.SimpleDateFormat;

@Api("后台商品控制器")
@Controller
@RequestMapping(path = "/pear")
@Slf4j
public class ProductController {

    @Autowired
    private SpringSecurityUtil springSecurityUtil;


    //发布商品页面
    @Operation("进入发布商品界面")
    @RequestMapping(path = "/topublish")
    public String toPublishProduct(Model model, HttpSession session, HttpServletRequest request) {

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
    public String publish(Product product, String brandName, String classifyName, String content, MultipartFile file,
            HttpSession session, HttpServletRequest request) {

        layuiJSON json = new layuiJSON(); //封装json数据传入前台
        System.out.println(brandName);
        System.out.println(classifyName);
        System.out.println(content);
        System.out.println(file);

        try {
            //发布操作代码
//            String res = articleFeign.publishArticle(article);

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

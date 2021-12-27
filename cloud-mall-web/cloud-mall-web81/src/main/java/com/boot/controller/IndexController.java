package com.boot.controller;

import com.boot.annotation.RateLimiter;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.pojo.Product;
import com.boot.pojo.VersionInfo;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author 游政杰
 * @date 2021/11/5 21:50
 */
@Controller
@Api("客户端首页 web api")
@Slf4j
@CrossOrigin
@RequestMapping(path = "/web/index")
public class IndexController {

    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private VersionInfoFallbackFeign versionInfoFallbackFeign;

//    @RateLimiter(permitsPerSecond = 100.0) //限流
    @RequestMapping(path = "/")
    public String index(HttpSession session,Model model)
    {
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }

        return "client/index";
    }


    @RequestMapping(path = "/view/homePage")
    public String homePage(HttpSession session,Model model)
    {
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }
        return "client/view/home";
    }


    @RequestMapping(path = "/cart")
    public String cart(HttpSession session,Model model)
    {
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }
        return "client/view/cart";
    }

    @RequestMapping(path = "/view/log")

    public String log(HttpSession session,Model model)
    {
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }
        return "client/view/common/log";
    }

    @RequestMapping(path = "/view/footer")
    public String footer(HttpSession session,Model model)
    {
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }
        return "client/view/common/footer";
    }

    @RequestMapping(path = "/view/homeMain")
    public String homeMain(HttpSession session,Model model)
    {
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }
        return "client/view/page/home.main";
    }

    @RequestMapping(path = "/view/homeHomeUserLogin")
    public String homeUserLogin()
    {
        return "client/view/page/home.userLogin";
    }

    @RequestMapping(path = "/view/homeProduct")
    public String homeProduct(HttpServletRequest request, Model model, HttpSession session)
    {
            try{
                Cookie[] cookies = request.getCookies();
                // 找到cookie名为cur_introduce_pid
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("cur_introduce_pid"))
                    {
                        //这里可能会报错,怕用户修改此cookie导致Long.valueof报错
                        Long v = Long.valueOf(cookie.getValue());
                        model.addAttribute("pid",v);
                    }
                }
                try{
                    String currentUser = springSecurityUtil.currentUser(session);
                    model.addAttribute("username",currentUser);
                }catch (Exception e){
                    model.addAttribute("username",null);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        return "client/view/page/home.product";
    }

    @RequestMapping(path = "/view/homeProductDetail/{pid}")
    public String homeProductDetail1(@PathVariable("pid") long pid,Model model, HttpServletRequest request,HttpSession session)
    {
        String[] strings = productFallbackFeign.selectIntroduceByPid(pid);
        model.addAttribute("imgs",strings);

        Product product = productFallbackFeign.selectProductByPid(pid);
        model.addAttribute("product",product);

        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }


        return "client/view/newpage/introduce";
    }

    @RequestMapping(path = "/view/homeProductDetail")
    public String homeProductDetail2(Model model, HttpServletRequest request,HttpSession session)
    {
        try{
            Cookie[] cookies = request.getCookies();
            // 找到cookie名为cur_introduce_pid
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("cur_introduce_pid"))
                {
                    //这里可能会报错,怕用户修改此cookie导致Long.valueof报错
                    Long v = Long.valueOf(cookie.getValue());
                    model.addAttribute("psid",v);
                }
            }
            try{
                String currentUser = springSecurityUtil.currentUser(session);
                model.addAttribute("username",currentUser);
            }catch (Exception e){
                model.addAttribute("username",null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "client/view/page/home.product.detail";
    }

    @RequestMapping(path = "/view/homeSearch")
    public String homeSearch()
    {
        return "client/view/page/home.search";
    }
    @RequestMapping(path = "/view/page404")
    public String page404()
    {
        return "client/view/404";
    }


    @RequestMapping(path = "/view/direct/focusImgMultTpl")
    public String focusImgMultTpl()
    {
        return "directives/template/focusImgMultTpl";
    }
    @RequestMapping(path = "/view/direct/focusImgTpl")
    public String focusImgTpl()
    {
        return "directives/template/focusImgTpl";
    }

    @RequestMapping(path = "/view/direct/ulBoxContentTpl")
    public String ulBoxContentTpl()
    {
        return "directives/template/ulBoxContentTpl";
    }
    @RequestMapping(path = "/view/direct/ulBoxContentTplMult")
    public String ulBoxContentTplMult()
    {
        return "directives/template/ulBoxContentTplMult";
    }
    @RequestMapping(path = "/view/direct/ulBoxTpl")
    public String ulBoxTpl()
    {
        return "directives/template/ulBoxTpl";
    }



    @GetMapping(path = "/view/sendCookie")
    @ResponseBody
    public String sendCookie(@RequestParam(value = "pid",required = false) long pid
                            ,HttpServletResponse response)
    {
        //发送cookie
        try{
            Cookie cookie = new Cookie("cur_introduce_pid",pid+"");
            response.addCookie(cookie);
            return "send success";
        }catch (Exception e){
            return "send fail";
        }
    }


    @GetMapping(path = "/view/getCookie")
    @ResponseBody
    public String getCookie(HttpServletRequest request)
    {
        try{
            Cookie[] cookies = request.getCookies();
            // 找到cookie名为cur_introduce_pid
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("cur_introduce_pid"))
                {
                    //这里可能会报错,怕用户修改此cookie导致Long.valueof报错
                    Long v = Long.valueOf(cookie.getValue());
                   return v+"";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    @GetMapping(path = "/buyNowPage/{productid}")
    public String buyNowPage(@PathVariable("productid") long productid,Model model,HttpSession session)
    {

        Product product = productFallbackFeign.selectProductByPid(productid);

        List<VersionInfo> versionInfos = versionInfoFallbackFeign.selectVersionInfoByPid(productid);

        int count = versionInfoFallbackFeign.selectOrderCountBypid(productid);


        model.addAttribute("versionInfoFallbackFeign",versionInfoFallbackFeign);
        model.addAttribute("count",count);
        model.addAttribute("product",product);
        model.addAttribute("versionInfos",versionInfos);

        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }


        return "client/view/newpage/buyNow";
    }





}

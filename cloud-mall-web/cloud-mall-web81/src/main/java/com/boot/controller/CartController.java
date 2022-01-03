package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.feign.product.fallback.CartFallbackFeign;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.feign.product.notFallback.CartFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Cart;
import com.boot.pojo.Product;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author 游政杰
 */
@Api("购物车服务 web api")
@Controller
@RequestMapping(path = "/web/cart")
public class CartController {

  @Autowired
  private SpringSecurityUtil springSecurityUtil;

  @Autowired
  private UserFallbackFeign userFallbackFeign;

  @Autowired
  private CartFeign cartFeign;
  @Autowired
  private CartFallbackFeign cartFallbackFeign;

  @Autowired
  private ProductFallbackFeign productFallbackFeign;

  @Autowired
  private VersionInfoFallbackFeign versionInfoFallbackFeign;

  //进入购物车
  @RequestMapping(path = "/tocart")
  public String tocart(HttpSession session, Model model)
  {
    try{
      String currentUser = springSecurityUtil.currentUser(session);
      model.addAttribute("username",currentUser);
    }catch (Exception e){
      model.addAttribute("username",null);
    }
    return "client/view/newpage/cart";
  }


  //分割versionInfo的skus
  private final long[] cutSkus(String skus)
  {
    String[] strings = skus.split(",");
    long []arr=new long[strings.length];
    for (int i = 0; i < strings.length; i++) {
      arr[i]=Long.parseLong(strings[i]);
    }
    return arr;
  }


  // 放入购物车
  @ResponseBody
  @PostMapping(path = "/pushCart")
  public CommonResult<Product> pushCart(@RequestParam(value = "skuarr[]",required = true) long[] skuarr,
                                        @RequestParam(value = "productid",required = true) long productid,
                                        HttpSession session) {

    CommonResult<Product> commonResult = new CommonResult<>();


    try{

      // 二次校验数组数据
      for (long e : skuarr) {

        if (e == 0) // 非法数据
        {
          commonResult.setCode(ResultCode.FAILURE);
          break;
        }
      }

      //这里分两种情况，一种是如果我们购物车有相同的商品（包括所选的版本）此时只需在原来的基础上+1即可
      String currentUser = springSecurityUtil.currentUser(session);
      long userid = userFallbackFeign.selectUserIdByName(currentUser);
      List<Cart> carts = cartFallbackFeign.selectCartByUserId(userid); //查询当前用户所有购物车
      boolean hasSame=false; //默认没有相同
      long cid=-1;

      //对比两数组是否完全一致算法
      out: for (Cart cart : carts) {
        long[] skus = cutSkus(cart.getSkus());

        if(productid==cart.getProductid()&&skus.length==skuarr.length){
          boolean b=true; //内部维护一个布尔值，当下面遍历如果两两不等就把b=false即可
          // 此时在深一步查询
          for (int i = 0; i < skuarr.length; i++) {
            if(skuarr[i]!=skus[i]) //有一个不等就改false，说明不相等
            {
              b=false;
              break;
            }
          }
          if(b){ //此时有相同
            //把hasSame标记改为true
            hasSame=true;
            //把这条购物车的id记录下来后面可以插入
            cid=cart.getId();
            break out;
          }
        }
      }

      if(hasSame){ //有相同的找到相同的加1

        CommonResult<Cart> cartCommonResult = cartFallbackFeign.selectCartByCartId(cid);

        Cart cart = cartCommonResult.getObj();

        int oldCount = cart.getGoodsCount(); //旧的数量
        BigDecimal oldTotalPrice = cart.getSingleGoodsMoney(); //旧的总价

        int newCount=oldCount+1;
        BigDecimal newTotalPrice=new BigDecimal("0");
        newTotalPrice = newTotalPrice.add(oldTotalPrice);
        newTotalPrice = newTotalPrice.add(cart.getPrice());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid",cid);
        jsonObject.put("newCount",newCount);
        jsonObject.put("newTotalPrice",newTotalPrice);


        cartFeign.updateCountAndTotalPrice(jsonObject); //通过Json对象传到其他服务模块


      }else if(!hasSame){ //没有相同就往购物车数据库插入数据
        Product product = productFallbackFeign.selectProductByPid(productid);
        Cart cart = new Cart();

        cart.setId(SnowId.nextId());
        cart.setImgUrl(product.getImg());
        cart.setGoodsInfo(product.getName());

        StringBuffer params=new StringBuffer();

        for (long l : skuarr) {
          String s = versionInfoFallbackFeign.selectNameByversionId(l);
          params.append(s+"  ");
        }

        cart.setGoodsParams(params.toString());
        cart.setPrice(new BigDecimal(String.valueOf(product.getPrice())));
        cart.setGoodsCount(1); //默认购买数量为1
        cart.setSingleGoodsMoney(new BigDecimal(String.valueOf(product.getPrice()))); //因为我们默认count=1，所以这个值也等于price值
        cart.setUserid(userid);
        cart.setProductid(productid);

        String skus="";

        skus+=skuarr[0];

        if(skuarr.length>1){

          for (int i = 1; i < skuarr.length; i++) {
            skus+=","+skuarr[i];
          }

        }

        cart.setSkus(skus);

        cartFeign.addProductToCart(cart);

      }


    }catch (Exception e){

      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }

    return commonResult;
  }


  //根据用户id查询购物车
  @ResponseBody
  @GetMapping(path = "/selectCartByUserId")
  public JSONObject selectCartByUserId(HttpSession session){

    String curname = springSecurityUtil.currentUser(session); //用户名是唯一的

    long id = userFallbackFeign.selectUserIdByName(curname);

    List<Cart> carts = cartFallbackFeign.selectCartByUserId(id);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("carts",carts);

    return jsonObject;
  }

  @ResponseBody
  @GetMapping(path = "/deleteCartBycartId")
  public CommonResult<Cart> deleteCartBycartId(@RequestParam(value = "cartid",required = true) long cartid)
  {
    CommonResult<Cart> commonResult = new CommonResult<>();
    try {
      cartFeign.deleteCartByCartId(cartid);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }


  }





}

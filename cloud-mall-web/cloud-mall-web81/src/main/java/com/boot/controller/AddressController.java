package com.boot.controller;

import com.boot.data.CommonResult;
import com.boot.feign.order.notFallback.AddressFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Address;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/web/address")
@Api("收货地址服务 web api")
public class AddressController {

  @Autowired
  private SpringSecurityUtil springSecurityUtil;

  @Autowired
  private AddressFeign addressFeign;

  @Autowired
  private UserFallbackFeign userFallbackFeign;

  @ResponseBody
  @PostMapping(path = "/addAddress")
  public CommonResult<Address> addAddress(Address address, HttpSession session) {
    address.setId(SnowId.nextId());

    String name = springSecurityUtil.currentUser(session);
    long userid = userFallbackFeign.selectUserIdByName(name);
    address.setUserid(userid);

    CommonResult<Address> commonResult = addressFeign.addAddress(address);

    return commonResult;
  }

  @ResponseBody
  @GetMapping(path = "/delAddress")
  public CommonResult<Address> delAddress(@RequestParam(value = "addressid",required = true)
                                                    long addressid){

    CommonResult<Address> commonResult = addressFeign.delAddressById(addressid);

    return commonResult;
  }


}

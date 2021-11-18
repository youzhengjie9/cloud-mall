package com.boot.controller;

import com.boot.pojo.Address;
import com.boot.service.AddressService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/address")
@Api("收货地址服务api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    //用户可能有多个地址，所以下面是一个集合
    @ResponseBody
    @GetMapping(path = "/selectAddressByUserId/{userid}")
    public List<Address> selectAddressByUserId(@PathVariable("userid") long userid){

        return addressService.selectAddressByUserId(userid);
    }

    //根据addressid查询Address
    @ResponseBody
    @GetMapping(path = "/selectAddressByid/{id}")
    public Address selectAddressByid(@PathVariable("id") long id){


        return addressService.selectAddressByid(id);
    }


}

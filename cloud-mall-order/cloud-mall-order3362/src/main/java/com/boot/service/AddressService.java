package com.boot.service;

import com.boot.pojo.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressService {

    //用户可能有多个地址，所以下面是一个集合
    List<Address> selectAddressByUserId(long userid);

    //根据addressid查询Address
    Address selectAddressByid(long id);

    void addAddress(Address address);

    void delAddressById(long id);
}

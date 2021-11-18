package com.boot.dao;

import com.boot.pojo.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddressMapper {

    //用户可能有多个地址，所以下面是一个集合
    List<Address> selectAddressByUserId(@Param("userid") long userid);


    //根据addressid查询Address
    Address selectAddressByid(@Param("id") long id);



}

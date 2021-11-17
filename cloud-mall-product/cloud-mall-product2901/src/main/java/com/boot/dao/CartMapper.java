package com.boot.dao;

import com.boot.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CartMapper {


    //根据用户id查询购物车
    List<Cart> selectCartByUserId(@Param("userid") long userid);




}

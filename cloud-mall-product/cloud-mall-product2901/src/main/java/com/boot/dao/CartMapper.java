package com.boot.dao;

import com.boot.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface CartMapper {


    //根据用户id查询购物车
    List<Cart> selectCartByUserId(@Param("userid") long userid);


    void  addProductToCart(Cart cart);


    Cart selectCartByCartId(@Param("cartid") long cartid);

    //修改指定购物车的购买数量和总价
    void updateCountAndTotalPrice(@Param("id") long id,
                                  @Param("goodsCount") int goodsCount,
                                  @Param("singleGoodsMoney") BigDecimal singleGoodsMoney);



}

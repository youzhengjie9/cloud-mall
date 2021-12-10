package com.boot.dao;

import com.boot.pojo.CouponsActivity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CouponsActivityMapper {


    void insertCouponsActivity(CouponsActivity couponsActivity);






}

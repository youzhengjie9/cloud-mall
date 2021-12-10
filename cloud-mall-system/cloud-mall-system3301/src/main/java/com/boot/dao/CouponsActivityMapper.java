package com.boot.dao;

import com.boot.pojo.CouponsActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponsActivityMapper {


    void insertCouponsActivity(CouponsActivity couponsActivity);

    List<CouponsActivity> selectAllCouponsActivityByLimit(@Param("page") int page,
                                                          @Param("size") int size);

    int selectCouponsActivityCount();

    void updateValid(@Param("id") long id,@Param("valid") int valid);

    void deleteCouponsActivity(@Param("id") long id);

    CouponsActivity selectCouponsActivityById(@Param("id") long id);

    void updateCouponsActivity(CouponsActivity couponsActivity);



}

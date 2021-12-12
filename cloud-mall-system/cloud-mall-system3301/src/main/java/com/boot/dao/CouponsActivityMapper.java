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

    List<CouponsActivity> selectAllCouponsActivityByLimitAndValid(@Param("page") int page,
                                                                  @Param("size") int size);

    int selectCouponsActivityCountByValid();

    //查询所有优惠券活动每人限领和最低门槛
    List<CouponsActivity> selectAllCouponsActivityLimitAndPoint();

    //查询活动优惠券数量
    int selectCouponsCount(@Param("id") long id);

    //扣除优惠券数量-1
    void decrCouponsCount(@Param("id") long id);



}

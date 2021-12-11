package com.boot.service;

import com.boot.pojo.CouponsActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponsActivityService {


    void insertCouponsActivity(CouponsActivity couponsActivity);

    List<CouponsActivity> selectAllCouponsActivityByLimit(int page, int size);

    int selectCouponsActivityCount();

    void updateValid(long id, int valid);

    void deleteCouponsActivity(long id);

    void batchDeleteCouponsActivity(long[] ids);

    CouponsActivity selectCouponsActivityById(long id);

    void updateCouponsActivity(CouponsActivity couponsActivity);

    List<CouponsActivity> selectAllCouponsActivityByLimitAndValid(int page, int size);

    int selectCouponsActivityCountByValid();

    //查询所有优惠券活动每人限领和最低门槛
    List<CouponsActivity> selectAllCouponsActivityLimitAndPoint();

}

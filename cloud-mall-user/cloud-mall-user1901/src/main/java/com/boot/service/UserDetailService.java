package com.boot.service;

import com.boot.pojo.UserDetail;
import org.apache.ibatis.annotations.Param;

public interface UserDetailService {


    void insertUserDetail(UserDetail userDetail);

    void updateSex(long userid,int sex);

    void updateSignature(long userid,String signature);

}

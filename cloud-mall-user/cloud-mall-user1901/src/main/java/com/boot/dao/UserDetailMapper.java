package com.boot.dao;

import com.boot.pojo.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDetailMapper {

  void insertUserDetail(UserDetail userDetail);

  void updateSex(@Param("userid") long userid, @Param("sex") int sex);

  void updateSignature(@Param("userid") long userid, @Param("signature") String signature);

  //修改头像
  void updateIcon(@Param("userid") long userid,@Param("icon") String icon);

  UserDetail selectUserDetail(@Param("userid") long userid);

}

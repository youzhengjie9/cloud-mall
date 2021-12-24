package com.boot.dao;

import com.boot.pojo.VersionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface VersionInfoMapper {

    //根据商品id查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPid(@Param("pid")long pid);

    //查询所有版本信息
    List<VersionInfo> selectAllVersionInfo();

    //通过pid查询order的数量（被去重后）
    int selectOrderCountBypid(@Param("pid")long pid);

    //根据商品id和order查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPidAndOrder(@Param("pid")long pid,@Param("order") long order);

    String selectVersionInfoTitle(@Param("pid")long pid,@Param("order") long order);

    String selectVersionInfoDesc(@Param("pid")long pid,@Param("order") long order);

    //查价格
    BigDecimal selectPriceByversionId(@Param("versionId") long versionId);


    String selectNameByversionId(@Param("versionId") long versionId);

    List<VersionInfo> selectVersionInfoByLimit(@Param("page") int page,
                                               @Param("size") int size);


    List<VersionInfo> selectVersionInfoByLimitAndPid(@Param("pid") long pid,@Param("page") int page,
                                               @Param("size") int size);

    int selectversionInfoCountByPid(@Param("pid") long pid);

    int selectAllversionInfoCount();

    int insertVersionInfo(VersionInfo versionInfo);

    int updateVersionInfo(VersionInfo versionInfo);

    int deleteVersionInfo(@Param("versionId") long versionId);

    VersionInfo selectVersionByVersionId(@Param("versionId") long versionId);

}

package com.boot.service;

import com.boot.pojo.VersionInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 游政杰
 */
public interface VersionInfoService {


    //根据商品id查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPid(long pid);

    //查询所有版本信息
    List<VersionInfo> selectAllVersionInfo();

    //通过pid查询order的数量（被去重后）
    int selectOrderCountBypid(long pid);

    //根据商品id和order查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPidAndOrder(long pid,long order);


    String selectVersionInfoTitle(long pid,long order);

    String selectVersionInfoDesc(long pid,long order);

    //查价格
    BigDecimal selectPriceByversionId(long versionId);

    String selectNameByversionId(long versionId);

    List<VersionInfo> selectVersionInfoByLimit(int page, int size);


    List<VersionInfo> selectVersionInfoByLimitAndPid(long pid,int page, int size);

    int selectversionInfoCountByPid(long pid);

    int selectAllversionInfoCount();

    int insertVersionInfo(VersionInfo versionInfo);

    int updateVersionInfo(VersionInfo versionInfo);

    int deleteVersionInfo(long versionId);

    int batchDeleteVersionInfo(long[] versionIds);

    VersionInfo selectVersionByVersionId(long versionId);

}

package com.boot.feign.product.fallback;

import com.boot.feign.product.fallback.impl.VersionInfoFallbackFeignImpl;
import com.boot.pojo.VersionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-product",fallback = VersionInfoFallbackFeignImpl.class)
public interface VersionInfoFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectVersionInfoByPid/{pid}")
    public List<VersionInfo> selectVersionInfoByPid(@PathVariable("pid") long pid);

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectAllVersionInfo")
    public List<VersionInfo> selectAllVersionInfo();

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectOrderCountBypid/{pid}")
    public int selectOrderCountBypid(@PathVariable("pid") long pid);


    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectVersionInfoByPidAndOrder/{pid}/{order}")
    public List<VersionInfo> selectVersionInfoByPidAndOrder(@PathVariable("pid") long pid,
                                                            @PathVariable("order") long order);

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectVersionInfoTitle/{pid}/{order}")
    public String selectVersionInfoTitle(@PathVariable("pid") long pid,
                                         @PathVariable("order") long order);

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectVersionInfoDesc/{pid}/{order}")
    public String selectVersionInfoDesc(@PathVariable("pid") long pid,
                                        @PathVariable("order") long order);

    // 查价格
    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectPriceByversionId/{versionId}")
    public BigDecimal selectPriceByversionId(@PathVariable("versionId") long versionId);

    //查询版本名称
    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/selectNameByversionId/{versionId}")
    public String selectNameByversionId(@PathVariable("versionId") long versionId);
}

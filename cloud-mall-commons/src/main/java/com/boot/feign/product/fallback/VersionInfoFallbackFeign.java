package com.boot.feign.product.fallback;

import com.boot.feign.product.fallback.impl.VersionInfoFallbackFeignImpl;
import com.boot.pojo.VersionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

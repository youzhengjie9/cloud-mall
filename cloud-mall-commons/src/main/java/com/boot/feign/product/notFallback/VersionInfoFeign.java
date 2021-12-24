package com.boot.feign.product.notFallback;

import com.boot.data.CommonResult;
import com.boot.pojo.VersionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-product")
public interface VersionInfoFeign {


    @ResponseBody
    @PostMapping(path = "/feign/versioninfo/insertVersionInfo")
    public int insertVersionInfo(@RequestBody VersionInfo versionInfo);

    @ResponseBody
    @PostMapping(path = "/feign/versioninfo/updateVersionInfo")
    public int updateVersionInfo(@RequestBody VersionInfo versionInfo);

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/deleteVersionInfo/{versionId}")
    public int deleteVersionInfo(@PathVariable("versionId") long versionId);

    @ResponseBody
    @GetMapping(path = "/feign/versioninfo/batchDeleteVersionInfo")
    public CommonResult<String> batchDeleteVersionInfo(@RequestParam("ids")long[] ids);


}

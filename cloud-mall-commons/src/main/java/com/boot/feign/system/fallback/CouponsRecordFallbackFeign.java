package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.CouponsRecordFallbackFeignImpl;
import com.boot.pojo.CouponsRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-system",fallback = CouponsRecordFallbackFeignImpl.class)
public interface CouponsRecordFallbackFeign {




}

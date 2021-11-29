package com.boot.feign.log.fallback;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.log.fallback.impl.TimeCalcFallbackFeignImpl;
import com.boot.pojo.TimeCalc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-log",fallback = TimeCalcFallbackFeignImpl.class)
public interface TimeCalcFallbackFeign {


    //分页查询
    @ResponseBody
    @GetMapping(path = "/feign/timecalc/selectTimeCalcBylimit/{page}/{size}")
    public List<TimeCalc> selectTimeCalcBylimit(@PathVariable("page") int page,
                                                @PathVariable("size") int size);


    @ResponseBody
    @GetMapping(path = "/feign/timecalc/selectTimeCalcCount")
    public int selectTimeCalcCount();

    @ResponseBody
    @PostMapping(path = "/feign/timecalc/selectTimeCalcByUriLimit")
    public List<TimeCalc> selectTimeCalcByUriLimit(@RequestBody JSONObject jsonObject);

    @ResponseBody
    @PostMapping(path = "/feign/timecalc/selectTimeCalcCountBylimit")
    public int selectTimeCalcCountBylimit(@RequestBody JSONObject jsonObject);


}

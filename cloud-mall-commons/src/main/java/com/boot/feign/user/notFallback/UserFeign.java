package com.boot.feign.user.notFallback;

import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-user")
public interface UserFeign {


    //减余额
    @ResponseBody
    @GetMapping(path = "/feign/user/decrMoneyByUserId/{userid}/{money}")
    public CommonResult<User> decrMoneyByUserId(@PathVariable("userid") long userid,
                                                @PathVariable("money") String money);

}

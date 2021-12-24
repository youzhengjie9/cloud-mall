package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.feign.product.notFallback.VersionInfoFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Classify;
import com.boot.pojo.VersionInfo;
import com.boot.utils.IpUtils;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("商品规格后台控制器")
public class SpecController {

    @Autowired
    private VersionInfoFallbackFeign versionInfoFallbackFeign;

    @Autowired
    private VersionInfoFeign versionInfoFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @RequestMapping(path = "/toSpec")
    public String toSpec()
    {

        return "back/spec_list";
    }

    @ResponseBody
    @RequestMapping(path = "/specData")
    public String classifyData(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "limit", defaultValue = "6") int limit,
                               @RequestParam(value = "pid",defaultValue = "")String pid)
    {
        page=limit*(page-1);
        if(StringUtils.isBlank(pid)){
            layuiData<VersionInfo> layuiData = new layuiData<>();

            List<VersionInfo> versionInfos = versionInfoFallbackFeign.selectVersionInfoByLimit(page, limit);
            int count = versionInfoFallbackFeign.selectAllversionInfoCount();
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(versionInfos);
            return JSON.toJSONString(layuiData);
        }else {

            layuiData<VersionInfo> layuiData = new layuiData<>();
            Long aLong = Long.valueOf(pid);
            List<VersionInfo> versionInfos = versionInfoFallbackFeign.selectVersionInfoByLimitAndPid(aLong, page, limit);
            int count = versionInfoFallbackFeign.selectversionInfoCountByPid(aLong);
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(versionInfos);
            return JSON.toJSONString(layuiData);

        }

    }

    @RequestMapping(path = "/addSpecPage")
    public String addSpecPage()
    {

        return "back/module/addSpec";
    }

    @Operation("添加规格")
    @ResponseBody
    @PostMapping(path = "/add/spec")
    public String addSpec(VersionInfo versionInfo) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            versionInfo.setVersionId(SnowId.nextId());
            versionInfoFeign.insertVersionInfo(versionInfo);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("添加规格成功");
            return JSON.toJSONString(layuiJSON);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("添加规格失败");
            return JSON.toJSONString(layuiJSON);
        }
    }

    @RequestMapping(path = "/modifySpecPage")
    public String modifySpecPage(String id, Model model) {

        VersionInfo versionInfo = versionInfoFallbackFeign.selectVersionByVersionId(Long.valueOf(id));
        model.addAttribute("versionInfo",versionInfo);
        model.addAttribute("id", id);

        return "back/module/editSpec";
    }

    @ResponseBody
    @PostMapping(path = "/modify/spec")
    public String modifySpec(VersionInfo versionInfo) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            versionInfoFeign.updateVersionInfo(versionInfo);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("修改规格成功");
            return JSON.toJSONString(layuiJSON);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("修改规格失败");
            return JSON.toJSONString(layuiJSON);
        }
    }


    @Operation("删除规格")
    @ResponseBody
    @RequestMapping(path = "/deleteSpec/{versionid}")
    public String deleteSpec(
            @PathVariable("versionid") String versionid, HttpSession session, HttpServletRequest request) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            long vid = Long.parseLong(versionid);

            // 删除
            versionInfoFeign.deleteVersionInfo(vid);
            layuiJSON.setMsg("删除规格成功");
            layuiJSON.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setMsg("删除规格失败");
            layuiJSON.setSuccess(false);
        }

        return JSON.toJSONString(layuiJSON);
    }

    @Operation("批量删除规格")
    @ResponseBody
    @RequestMapping(path = "/batchDeleteSpec/{checkIds}")
    public String batchDeleteSpec(@PathVariable("checkIds") String checkIds) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            String[] split = checkIds.split(",");
            long[] arr = new long[split.length];

            for (int i = 0; i < split.length; i++) {
                arr[i] = Long.parseLong(split[i]);
            }
            versionInfoFeign.batchDeleteVersionInfo(arr);

            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("批量删除失败");
        }

        return JSON.toJSONString(layuiJSON);
    }

}

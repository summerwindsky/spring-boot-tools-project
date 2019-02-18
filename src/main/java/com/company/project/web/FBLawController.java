package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.other.ft.utils.Flfg;
import com.company.project.other.ft.utils.LawUtil;
import com.company.project.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* Created by CodeGenerator on 2018/09/25.
*/
@Controller
@RequestMapping("/fb")
public class FBLawController {
    @Resource
    private UserInfoService userInfoService;

    @ResponseBody
    @RequestMapping(value = "/ft/{flmc}", produces = "application/json;charset=utf-8")
    public Flfg start(@PathVariable String flmc) {
        return LawUtil.getFv(LawUtil.getLawDetail(flmc));
    }

    @ResponseBody
    @RequestMapping(value = "/ft", produces = "application/json;charset=utf-8")
    public Flfg runTimedTask(HttpServletRequest request) {
        String flmc = request.getParameter("flmc");
        Flfg flfg = null;
        if (StringUtils.isNotEmpty(flmc)) {
            flfg  = LawUtil.getFv(LawUtil.getLawDetail(flmc));
        }
        return flfg;
    }

}

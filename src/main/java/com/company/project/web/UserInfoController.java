package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
import com.company.project.service.UserInfoService;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2018/09/25.
*/
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @ResponseBody
    @RequestMapping(value = "/start/{id}", produces = "application/json;charset=utf-8")
    public String start(@PathVariable String id) {

        userInfoService.run(id);
        JSONObject json = new JSONObject();
        json.put("stat", "start success");
        return json.toString();
    }

}

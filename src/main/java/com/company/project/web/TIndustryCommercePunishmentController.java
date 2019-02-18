package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.TIndustryCommercePunishment;
import com.company.project.service.TIndustryCommercePunishmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/01/09.
*/
@RestController
@RequestMapping("/zhgg")
public class TIndustryCommercePunishmentController {
    private static final Logger logger = LoggerFactory.getLogger(TIndustryCommercePunishmentController.class);
    @Resource
    private TIndustryCommercePunishmentService tIndustryCommercePunishmentService;

    @ResponseBody
    @RequestMapping(value = "/start", produces = "application/json;charset=utf-8")
    public String start() {
        tIndustryCommercePunishmentService.iterate();
        JSONObject json = new JSONObject();
        json.put("stat", "start success");
        logger.info("start success!");
        return json.toString();
    }
}

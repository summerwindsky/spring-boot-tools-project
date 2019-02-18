package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.model.TQkwz;
import com.company.project.service.TQkwzService;
import com.company.project.utils.GsonUtil;
import com.company.project.system.es.QueryUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by CodeGenerator on 2018/11/01.
 */
@RestController
@RequestMapping("/qkwz")
public class TQkwzController {

    private static final Logger logger = LoggerFactory.getLogger(TQkwzController.class);
    @Resource
    private TQkwzService tQkwzService;


    @ResponseBody
    @RequestMapping(value = "/start", produces = "application/json;charset=utf-8")
    public String start() {
        tQkwzService.iterate();
        JSONObject json = new JSONObject();
        json.put("stat", "start success");
        logger.info("start success!");
        return json.toString();
    }


}

package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.service.TXsXsgdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/11/01.
 */
@RestController
@RequestMapping("/xsgd")
public class TXsXsgdController {

    private static final Logger logger = LoggerFactory.getLogger(TXsXsgdController.class);

    @Resource
    private TXsXsgdService tXsXsgdService;

    @ResponseBody
    @RequestMapping(value = "/start", produces = "application/json;charset=utf-8")
    public String start() {
        logger.info("===========学术观点================");

        tXsXsgdService.iterate();
        JSONObject json = new JSONObject();
        json.put("stat", "start success");
        logger.info("start success!");
        return json.toString();
    }

}

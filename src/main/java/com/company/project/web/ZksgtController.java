package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.utils.EncodeUtils;
import com.company.project.system.es.QueryUtil;
import com.company.project.utils.XmlUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/11/01.
 */
@RestController
@RequestMapping("/zksgt")
public class ZksgtController {

    private static final Logger logger = LoggerFactory.getLogger(ZksgtController.class);

    private static final String INDEX = "zksgt_0118";

    @ResponseBody
    @RequestMapping(value = "/start", produces = "application/json;charset=utf-8")
    public String start() throws IOException {
        logger.info("===========start================");
//        String flmcPath = "files/法律名称.txt";
//        String flmcPath1 = "files/flmc_chl.txt";
//        String flmcPath2 = "files/flmc_lar.txt";
//        String ayPath = "files/案由.xml";
//        String fyPath = "files/法院.xml";
        String flmcPath = "files/zksgt/LAW.xml";
        String fyPath = "files/zksgt/FY.xml";
        String ayPath = "files/zksgt/AY.xml";
//        String lawFirmPath = "files/zksgt/LS-lawfirm.txt";
        String lawFirmPath = "files/zksgt/LS-lawfirm_new.txt";

        exeFile(lawFirmPath, "LS");
        exeXml(ayPath,"//AY", "MC", "AY");
        exeXml(fyPath, null,"name", "FY");
        exeXml(flmcPath, null,null, "LAW");

        JSONObject json = new JSONObject();
        json.put("stat", "start success");
        logger.info("start success!");
        return json.toString();
    }

    public void exeFile(String path, String lx) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        List<String> strings = IOUtils.readLines(inputStream);
        for (String string : strings) {
            insertEs(lx, string);
        }
        logger.info("文件：{} 处理完成", path);
    }

    public void exeXml(String path, String xpath, String attrName, String lx) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        Document doc = XmlUtils.readXml(inputStream);
        Element rootElement = doc.getRootElement();
        List<Element> elements = null;
        if (StringUtils.isEmpty(xpath)) {
            elements = (List<Element>) rootElement.elements();
        } else {
            elements = (List<Element>) rootElement.selectNodes(xpath);
        }
        for (Element element : elements) {

            // 没属性名，获取text
            if (StringUtils.isEmpty(attrName)) {
                insertEs(lx, element.getText());
            } else {
                String value = element.attributeValue(attrName);
                if (StringUtils.isNotEmpty(value)) {
                    insertEs(lx, value);
                }
            }
        }
        logger.info("xml文件：{} 处理完成", path);
    }

    public void insertEs(String lx, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("LX", lx);
        jsonObject.put("NAME", value);
        QueryUtil.index(INDEX, "zksuggest", null, EncodeUtils.randomUuid(), jsonObject, false);
    }

}

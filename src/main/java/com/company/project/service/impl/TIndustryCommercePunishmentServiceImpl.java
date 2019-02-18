package com.company.project.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.config.zhgg.KeyWordConfig;
import com.company.project.dao.TIndustryCommercePunishmentMapper;
import com.company.project.model.TIndustryCommercePunishment;
import com.company.project.model.TQkwz;
import com.company.project.model.es.LawBase;
import com.company.project.model.es.TIndustryCommercePunishmentESBean;
import com.company.project.service.TIndustryCommercePunishmentService;
import com.company.project.core.AbstractService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Service
@Transactional
public class TIndustryCommercePunishmentServiceImpl extends AbstractService<TIndustryCommercePunishment> implements TIndustryCommercePunishmentService {

    private static final Logger logger = LoggerFactory.getLogger(TIndustryCommercePunishmentServiceImpl.class);

    @Resource
    private TIndustryCommercePunishmentMapper tIndustryCommercePunishmentMapper;

    private String INDEX = "zhgg_0109";

    @Override
    public void iterate() {
        logger.info("===========知乎广告================");
        Condition condition = new Condition(TQkwz.class);
        condition.createCriteria().andCondition("status = '20'");
        List<TIndustryCommercePunishment> list = tIndustryCommercePunishmentMapper.selectByCondition(condition);
        for (TIndustryCommercePunishment tIndustryCommercePunishment : list) {

            TIndustryCommercePunishmentESBean esBean = new TIndustryCommercePunishmentESBean();
            try {
                BeanUtils.copyProperties(esBean, tIndustryCommercePunishment);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<LawBase> lawBases = new ArrayList<>();
            String lawBaseJson = esBean.getLawBase();
            if (StringUtils.isNotEmpty(lawBaseJson)) {
                JSONArray jsonArray = JSONArray.parseArray(lawBaseJson);
                for (Object o : jsonArray) {
                    JSONObject jsonObj = (JSONObject) o;
                    String title = jsonObj.getString("title");
                    String url = jsonObj.getString("url");
                    if (StringUtils.isNotEmpty(title)) {
                        title.replaceAll(" ", "");
                        lawBases.add(new LawBase(title, url));
                    }
                }
                esBean.setLawBaseList(lawBases);
            }

            String pdfFormatContent = esBean.getPdfFormatContent();
            if (StringUtils.isNotEmpty(pdfFormatContent)) {
                Set<String> sensitiveWords = new HashSet<>();
                for (String keyword : KeyWordConfig.set) {
                    if (pdfFormatContent.contains(keyword)) {
                        sensitiveWords.add(keyword);
                    }
                }
                esBean.setSensitiveWords(sensitiveWords);
            }

        }
    }
}

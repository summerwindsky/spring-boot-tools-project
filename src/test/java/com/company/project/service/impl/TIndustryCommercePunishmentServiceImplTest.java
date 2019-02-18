package com.company.project.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.model.es.LawBase;
import com.company.project.model.es.TIndustryCommercePunishmentESBean;
import com.company.project.service.TIndustryCommercePunishmentService;
import com.company.project.utils.GsonUtil;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Title:
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author zhangjing
 * @version 1.0
 * @date 2019-01-09 13:53
 */
public class TIndustryCommercePunishmentServiceImplTest {
    @Test
    public void iterate() throws Exception {
        String json = "[{\"title\":\"《中华人民共和国广告法》\",\"url\":\"http://law.wkinfo.com.cn//search/process?collection=adminPenalty&sp_term=%E3%80%8A%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%B9%BF%E5%91%8A%E6%B3%95%E3%80%8B&termField=lawAccording&accuracy=accuracy\"},{\"title\":\"《中华人民共和国广告法》第十九条\",\"url\":\"http://law.wkinfo.com.cn//search/process?collection=adminPenalty&sp_term=%E3%80%8A%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%B9%BF%E5%91%8A%E6%B3%95%E3%80%8B+%E7%AC%AC%E5%8D%81%E4%B9%9D%E6%9D%A1&termField=lawAccording&accuracy=accuracy\"},{\"title\":\"《中华人民共和国广告法》 第四十一条\",\"url\":\"http://law.wkinfo.com.cn//search/process?collection=adminPenalty&sp_term=%E3%80%8A%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%B9%BF%E5%91%8A%E6%B3%95%E3%80%8B+%E7%AC%AC%E5%9B%9B%E5%8D%81%E4%B8%80%E6%9D%A1&termField=lawAccording&accuracy=accuracy\"}]";
        List<LawBase> lawBases = new ArrayList<>();
        if (StringUtils.isNotEmpty(json)) {
            JSONArray jsonArray = JSONArray.parseArray(json);
            for (Object o : jsonArray) {
                JSONObject jsonObj = (JSONObject) o;
                String title = jsonObj.getString("title");
                String url = jsonObj.getString("url");
                if (StringUtils.isNotEmpty(title)) {
                    title.replaceAll(" ", "");
                    lawBases.add(new LawBase(title, url));
                }
            }
        }

        lawBases.forEach(x -> System.out.println(x.getTitle() + " " + x.getUrl()));

    }

    @Test
    public void testIter() {
        TIndustryCommercePunishmentService service = new TIndustryCommercePunishmentServiceImpl();
        service.iterate();
    }

}
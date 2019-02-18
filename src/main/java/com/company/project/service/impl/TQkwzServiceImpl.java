package com.company.project.service.impl;

import com.company.project.dao.TQkwzMapper;
import com.company.project.model.TQkwz;
import com.company.project.service.TQkwzService;
import com.company.project.core.AbstractService;
import com.company.project.system.es.QueryUtil;
import com.company.project.utils.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by CodeGenerator on 2018/11/01.
 */
@Service
@Transactional
public class TQkwzServiceImpl extends AbstractService<TQkwz> implements TQkwzService {
    @Resource
    private TQkwzMapper tQkwzMapper;
    private static final Logger logger = LoggerFactory.getLogger(TQkwzServiceImpl.class);

    private Pattern pp1 = Pattern.compile("<a href[^>]*>");
    private Pattern pp2 = Pattern.compile("</a>");

    private String INDEX = "qkwz_1101";
    private String TYPE = "writAnalyzer";

    @Override
    public void iterate() {
        logger.info("===========期刊文章================");
        Condition condition = new Condition(TQkwz.class);
        condition.createCriteria().andCondition("!ISNULL(Info_Id) AND Info_Id !='' AND !ISNULL(Info_Ctitle) AND Info_Ctitle !='' AND !ISNULL(Content_Txt) AND Content_Txt !=''");
        List<TQkwz> list = tQkwzMapper.selectByCondition(condition);
        for (int i = 0; i < list.size(); i++) {
            TQkwz tXsXsgd = list.get(i);
            Integer id = tXsXsgd.getId();
            tXsXsgd.setId(null);

            String contentTxt = tXsXsgd.getContentTxt();

            if (StringUtils.isNotEmpty(contentTxt)) {
                // 去除a标签
                tXsXsgd.setContentTxt(contentTxt.replaceAll("<a[\\s\\S]*?>([\\s\\S]*?)</a>", "$1"));
            }
            String json = GsonUtil.toJson(tXsXsgd);
            Map map = GsonUtil.gson.fromJson(json, Map.class);
            map.put("timestamp", System.currentTimeMillis());

//            BatchBean batchBean = new BatchBean("xsgd_1101", "writAnalyzer", null, id.toString(), GsonUtil.toJson(map));
//            BatchDeal.batchExecute(Arrays.asList(batchBean));

            QueryUtil.index(INDEX, TYPE, null, id.toString(), GsonUtil.toJson(map), false);

        }
    }
}

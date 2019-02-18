package com.company.project.service.impl;

import com.company.project.dao.TXsXsgdMapper;
import com.company.project.model.TQkwz;
import com.company.project.model.TXsXsgd;
import com.company.project.service.TXsXsgdService;
import com.company.project.core.AbstractService;
import com.company.project.utils.GsonUtil;
import com.company.project.system.es.QueryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/11/01.
 */
@Service
@Transactional
public class TXsXsgdServiceImpl extends AbstractService<TXsXsgd> implements TXsXsgdService {
    @Resource
    private TXsXsgdMapper tXsXsgdMapper;

    private String INDEX = "xsgd_1101";
    private String TYPE = "writAnalyzer";

    @Override
    public void iterate() {
        Condition condition = new Condition(TXsXsgd.class);
        condition.createCriteria().andCondition("LENGTH(xsfbId)<10 AND !ISNULL(xsfbId) AND xsfbId !='' AND !ISNULL(xsfbName) AND xsfbName !='' AND !ISNULL(xsfbContent) AND xsfbContent !=''");
        List<TXsXsgd> list = tXsXsgdMapper.selectByCondition(condition);
        for (int i = 0; i < list.size(); i++) {
            TXsXsgd tXsXsgd = list.get(i);
            Integer id = tXsXsgd.getId();
            tXsXsgd.setId(null);

            String xsfbbody = tXsXsgd.getXsfbbody();
            if (StringUtils.isNotEmpty(xsfbbody)) {
                tXsXsgd.setXsfbbody(xsfbbody.replaceAll("<a[\\s\\S]*?>([\\s\\S]*?)</a>", "$1"));
            }

            String json = GsonUtil.toJson(tXsXsgd);
            Map map = GsonUtil.gson.fromJson(json, Map.class);
            map.put("ajlx", "刑事");
            map.put("timestamp", System.currentTimeMillis());

//            BatchBean batchBean = new BatchBean("xsgd_1101", "writAnalyzer", null, id.toString(), GsonUtil.toJson(map));
//            BatchDeal.batchExecute(Arrays.asList(batchBean));

            QueryUtil.index(INDEX, TYPE, null, id.toString(), GsonUtil.toJson(map), false);

        }
    }


    public static void main(String[] args) {

        String content = "<a href=javascript:SLC(17010,0) class=alink onmouseover=AJI(17010,0)>刑法</a>";
        content = "《关于执行<a href=javascript:SLC(17010,0) class=alink onmouseover=AJI(17010,0)>刑法</a>确定罪名的规定》";

        String $1 = content.replaceAll("<a[\\s\\S]*?>([\\s\\S]*?)</a>", "$1");

    }
}

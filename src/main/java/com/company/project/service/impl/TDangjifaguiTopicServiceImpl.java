package com.company.project.service.impl;

import com.company.project.dao.TDangjifaguiTopicMapper;
import com.company.project.model.TDangjifaguiTopic;
import com.company.project.service.TDangjifaguiTopicService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class TDangjifaguiTopicServiceImpl extends AbstractService<TDangjifaguiTopic> implements TDangjifaguiTopicService {
    @Resource
    private TDangjifaguiTopicMapper tDangjifaguiTopicMapper;

}

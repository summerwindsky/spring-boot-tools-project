package com.company.project.service.impl;

import com.company.project.dao.FlfgSortedMapper;
import com.company.project.model.FlfgSorted;
import com.company.project.service.FlfgSortedService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/18.
 */
@Service
@Transactional
public class FlfgSortedServiceImpl extends AbstractService<FlfgSorted> implements FlfgSortedService {
    @Resource
    private FlfgSortedMapper flfgSortedMapper;

}

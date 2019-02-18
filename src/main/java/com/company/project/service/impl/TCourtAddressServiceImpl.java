package com.company.project.service.impl;

import com.company.project.dao.TCourtAddressMapper;
import com.company.project.model.TCourtAddress;
import com.company.project.service.TCourtAddressService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/11.
 */
@Service
@Transactional
public class TCourtAddressServiceImpl extends AbstractService<TCourtAddress> implements TCourtAddressService {
    @Resource
    private TCourtAddressMapper tCourtAddressMapper;

}

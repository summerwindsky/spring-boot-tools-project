package com.company.project.service.impl;

import com.company.project.dao.UserInfoMapper;
import com.company.project.model.UserInfo;
import com.company.project.service.UserInfoService;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title:
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author zhangjing
 * @version 1.0
 * @date 2018-11-05 15:25
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void run(String id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(3);
        userInfo.setName("Jerry");
        userInfoMapper.addUser(userInfo);
    }

    public void addUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("Jerry");
        userInfoMapper.addUser(userInfo);
    }

    public void getUserById() {
        UserInfo userInfo = userInfoMapper.getUserById(1);
        System.out.println(userInfo.getId() + ":" + userInfo.getName());
    }

    public void getUserByName() {
        UserInfo userInfo = userInfoMapper.getUserByName("Jerry");
        System.out.println(userInfo.getId() + ":" + userInfo.getName());
    }

    public void deleteUser() {
        userInfoMapper.deleteUser(1);
        List<UserInfo> users = userInfoMapper.getUsers();
        users.stream().forEach(System.out::println);
    }
}

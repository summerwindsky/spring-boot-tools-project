package com.company.project.dao;

import com.company.project.model.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserInfoMapper {

    @Insert("upsert into USER_INFO(ID,NAME) VALUES(#{user.id},#{user.name})")
    void addUser(@Param("user") UserInfo userInfo);

    @Delete("delete from USER_INFO WHERE ID=${userId}")
    void deleteUser(@Param("userId") int userId);

    @Select("select * from USER_INFO WHERE ID=#{userId}")
    UserInfo getUserById(@Param("userId") int userId);

    @Select("select * from USER_INFO WHERE NAME=#{userName} limit 1")
    UserInfo getUserByName(@Param("userName") String userName);

    @Select("select * from USER_INFO")
    List<UserInfo> getUsers();
}

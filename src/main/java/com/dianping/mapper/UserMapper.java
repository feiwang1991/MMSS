package com.dianping.mapper;

import com.dianping.po.User;

import java.util.List;

/**
 * created by IntelliJ IDEA
 */
public interface UserMapper {
    //根据ID查询用户
    public User findUserById(int id) throws Exception;
    //根据用户名查询所有用户
    public List<User> findUserByName(String name) throws Exception;
    //插入用户
    public void insertUser(User user) throws Exception;
    //删除用户
    public void deleteUserById(int id) throws  Exception;
}

package com.dianping.dao;

import com.dianping.po.User;

/**
 * created by IntelliJ IDEA
 * 用户Dao接口
 */
public interface UserDao {
    //通过ID查询用户
    public User findUserById(int id) throws Exception;

    //添加用户
    public void insertUser(User user) throws Exception;

    //根据ID删除用户
    public void deleteUserById(int id) throws Exception;


}

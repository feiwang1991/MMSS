package com.dianping.dao;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * UserDao接口实现类
 * author：王斐
 * create：2017-01-22  20:29
 */

public class UserDaoImpl implements UserDao {
    //在spring中需要注入SqlSessionFactory来为每个线程产生sqlsession，进而操作数据库，此处模拟spring注入
    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public User findUserById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("test.findUserById", id);
        sqlSession.close();
        return user;
    }

    public void insertUser(User user) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("test.insertUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteUserById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("test.deleteUserById",id);
        sqlSession.commit();
        sqlSession.close();
    }
}

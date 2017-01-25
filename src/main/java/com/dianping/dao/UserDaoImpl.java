package com.dianping.dao;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * UserDao接口实现类
 * author：王斐
 * create：2017-01-22  20:29
 */

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
    //在spring中需要注入SqlSessionFactory来为每个线程产生sqlsession，进而操作数据库，此处模拟spring注入
    //在和spring整合之后，继承sqlsessiondaosupport它有SqlSessionFactory属性和set方法，不需要再写一次。
    //获取sqlsession时候，直接调用父类的getSqlSession方法，该方法被重新封装过，拥有自动结束后关闭sqlsession的功能
    /*private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }*/


    public User findUserById(int id) throws Exception {
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession=this.getSqlSession();
        User user = sqlSession.selectOne("test.findUserById", id);
        //sqlSession.close();被spring封装后，新的sqlsession执行完方法后会自动进行关闭
        return user;
    }

    public void insertUser(User user) throws Exception {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession=this.getSqlSession();
        sqlSession.insert("test.insertUser",user);
        sqlSession.commit();
//        sqlSession.close();
    }

    public void deleteUserById(int id) throws Exception {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession=this.getSqlSession();
        sqlSession.delete("test.deleteUserById",id);
        sqlSession.commit();
//        sqlSession.close();
    }
}

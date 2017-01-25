package com.dianping.po;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
* User Tester. 
* 
* @author <Authors name>
* @version 1.0 
*/ 
public class UserTest { 

    @Test
    public void findUserByIdTest() throws Exception{
        //1 获得SqlSessionFactory
        //  1)获得SqlMapConfig.xml系统配置文件的具体位置，默认在classpath下，即classes下
        String reource= "mybatis/SqlMapConfig.xml";
        //  2)把配置文件转为输入流，准备传入sqlsessionfactorybuilder中
        InputStream input = Resources.getResourceAsStream(reource);
        //  3)根据获得的配置文件输入流，利用builder创建sqlsessionfactory
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
        //2 根据会话工厂得到SqlSession
        SqlSession sqlSession=sqlSessionFactory.openSession();
        //3 根据得到的会话SqlSession操作数据库
        /*注意：mybatis操作数据库不是根据session直接操作增删改查，而是session操作statement,此statement会自动封装我们在mapper.xml
          中写的sql语句，因此Mybatis可以做到直接编写sql来操作数据库。
         */
        //第一个参数为statement的Id,第二个参数为输入变量
        //注意statement 有namespace test, 格式为namespace.statementId
        User u=sqlSession.selectOne("test.findUserById",1);
        System.out.println(u);
        //关闭会话，连接池中一个连接对应一个会话，关闭会话后，连接进入连接池
        sqlSession.close();
    }
    @Test
    public void findUserByNameTest() throws Exception{
        String reource= "mybatis/SqlMapConfig.xml";
        InputStream input = Resources.getResourceAsStream(reource);
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        //注意statement 有namespace test, 格式为namespace.statementId
        List<User> list=sqlSession.selectList("test.findUserByName", "张三");
        for (User user : list) {
            System.out.println(user);
        }
        //关闭会话，连接池中一个连接对应一个会话，关闭会话后，连接进入连接池
        sqlSession.close();
    }
    @Test
    public void insertUserTest() throws Exception{
        String reource= "mybatis/SqlMapConfig.xml";
        InputStream input = Resources.getResourceAsStream(reource);
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        //注意statement 有namespace test, 格式为namespace.statementId
        User u=new User();
        u.setUsername("小王2");
        u.setBirthday(new Date());
        u.setSex("男");
        u.setAddress("非洲尼日尼亚2");
        sqlSession.insert("test.insertUser", u);
        sqlSession.commit();
        //关闭会话，连接池中一个连接对应一个会话，关闭会话后，连接进入连接池
        System.out.println(u.getId());
        sqlSession.close();
    }
    @Test
    public void deleteUserTest() throws Exception{
        String reource= "mybatis/SqlMapConfig.xml";
        InputStream input = Resources.getResourceAsStream(reource);
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        sqlSession.delete("test.deleteUserById", 5);
        sqlSession.commit();
        //关闭会话，连接池中一个连接对应一个会话，关闭会话后，连接进入连接池
        sqlSession.close();
    }
    @Test
    public void updateUserTest() throws Exception{
        String reource= "mybatis/SqlMapConfig.xml";
        InputStream input = Resources.getResourceAsStream(reource);
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        //注意statement 有namespace test, 格式为namespace.statementId
        User u=new User();
        u.setId(4);
        u.setUsername("大王");
        u.setBirthday(new Date());
        u.setSex("男");
        u.setAddress("非洲尼日尼亚2");
        sqlSession.update("test.updateUser",u);
        sqlSession.commit();
        //关闭会话，连接池中一个连接对应一个会话，关闭会话后，连接进入连接池
        sqlSession.close();
    }


} 

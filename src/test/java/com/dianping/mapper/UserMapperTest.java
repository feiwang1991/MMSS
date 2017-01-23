package com.dianping.mapper;

import com.dianping.po.User;
import com.dianping.po.UserCustom;
import com.dianping.vo.UserQueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * created by IntelliJ IDEA
 */
public class UserMapperTest {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void setUp() throws Exception {
        String xml="SqlMapConfig.xml";
        InputStream input= Resources.getResourceAsStream(xml);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
    }

    @Test
    public void testFindUserById() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        User u=userMapper.findUserById(1);
        sqlSession.close();
        System.out.println(u);
    }

    @Test
    public void testFindUserByName() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        List<User> list=userMapper.findUserByName("张三");
        for (User user : list) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void testFindUserByList() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        UserCustom userCustom=new UserCustom();
        userCustom.setUsername("张");
        userCustom.setSex("男");
        UserQueryVo userQueryVo=new UserQueryVo();
        userQueryVo.setUserCustom(userCustom);
        //添加for each查询
        List<Integer> ids=new ArrayList<Integer>();
        ids.add(1);
        ids.add(3);
        ids.add(10);
        userQueryVo.setIds(ids);
        List<UserCustom> list=userMapper.findUserByList(userQueryVo);
        for (UserCustom user : list) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void testFindUserByListCount() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        UserCustom userCustom=new UserCustom();
        userCustom.setUsername("小王");
        userCustom.setSex("男");
        UserQueryVo userQueryVo=new UserQueryVo();
        userQueryVo.setUserCustom(userCustom);
        int count=userMapper.findUserByListCount(userQueryVo);
        System.out.println(count);
        sqlSession.close();
    }
    @Test
    public void testFindUserByIdResultMap() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        User user=userMapper.findUserByIdResultMap(3);
        System.out.println(user);
        sqlSession.close();
    }
    @Test
    public void testInsertUser() throws Exception {

    }

    @Test
    public void testDeleteUserById() throws Exception {

    }
}
package com.dianping.dao;

import com.dianping.dao.UserDao;
import com.dianping.dao.UserDaoImpl;
import com.dianping.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;

/**
 * UserDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class UserDaoImplTest {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void before() throws Exception {
        String xml="SqlMapConfig.xml";
        InputStream input= Resources.getResourceAsStream(xml);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
    }

    /**
     * Method: findUserById(int id)
     */
    @Test
    public void testFindUserById() throws Exception {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User u=userDao.findUserById(1);
        System.out.println(u);
    }

    /**
     * Method: insertUser(User user)
     */
    @Test
    public void testInsertUser() throws Exception {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User u=new User();
        u.setUsername("小王3");
        u.setBirthday(new Date());
        u.setSex("女");
        u.setAddress("非洲尼日尼亚3");
        userDao.insertUser(u);
        System.out.println(u.getId());
    }

    /**
     * Method: deleteUserById(int id)
     */
    @Test
    public void testDeleteUserById() throws Exception {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        userDao.deleteUserById(9);

    }


} 

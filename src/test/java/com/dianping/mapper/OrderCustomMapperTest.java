package com.dianping.mapper;

import com.dianping.po.Orders;
import com.dianping.po.User;
import com.dianping.pojo.OrdersCustom;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * created by IntelliJ IDEA
 */
public class OrderCustomMapperTest {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void setUp() throws Exception {
        String xml="SqlMapConfig.xml";
        InputStream input= Resources.getResourceAsStream(xml);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(input);
    }

    @Test
    public void testFindOrderCustom() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrderCustomMapper orderCustomMapper=sqlSession.getMapper(OrderCustomMapper.class);
        List<OrdersCustom> orders=orderCustomMapper.findOrderCustom();
        for (OrdersCustom order : orders) {
            System.out.println(order);
        }
        sqlSession.close();
    }@Test
    public void testFindOrderCustomResultMap() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrderCustomMapper orderCustomMapper=sqlSession.getMapper(OrderCustomMapper.class);
        List<Orders> orders=orderCustomMapper.findOrderCustomResultMap();
        sqlSession.close();
    }
    @Test
    public void testFindOrderAndOrderDetail() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrderCustomMapper orderCustomMapper=sqlSession.getMapper(OrderCustomMapper.class);
        List<Orders> orders=orderCustomMapper.findOrderAndOrderDetail();
        sqlSession.close();
    }
    @Test
    public void testFindUserAndItems() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrderCustomMapper orderCustomMapper=sqlSession.getMapper(OrderCustomMapper.class);
        List<User> users=orderCustomMapper.findUserAndItems();
        sqlSession.close();
    }
    @Test
    public void testFindOrderUserLazyLoad() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrderCustomMapper orderCustomMapper=sqlSession.getMapper(OrderCustomMapper.class);
        List<Orders> orderses=orderCustomMapper.findOrderUserLazyLoad();
        for (Orders orderse : orderses) {
            System.out.println(orderse.getUser());
        }
        sqlSession.close();
    }
    //测试一级缓存
    @Test
    public void testCache1() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        User user=userMapper.findUserById(1);
        System.out.println(user);
        user.setUsername("张三三");
        userMapper.updateUser(user);
        sqlSession.commit();
        user=userMapper.findUserById(1);
        System.out.println(user);
    }
    //测试二级缓存
    @Test
    public void testCache2() throws Exception {
        SqlSession sqlSession1=sqlSessionFactory.openSession();
        SqlSession sqlSession2=sqlSessionFactory.openSession();
        SqlSession sqlSession3=sqlSessionFactory.openSession();

        UserMapper userMapper1=sqlSession1.getMapper(UserMapper.class);
        User user=userMapper1.findUserById(1);
        System.out.println(user);
        sqlSession1.close();//关闭后才能写入到二级缓存中

        /*UserMapper userMapper3=sqlSession3.getMapper(UserMapper.class);
        User user3=userMapper3.findUserById(1);
        user3.setUsername("张三三2");
        userMapper3.updateUser(user3);
        sqlSession3.commit();//清除二级缓存
        sqlSession3.close();*/

        UserMapper userMapper2=sqlSession2.getMapper(UserMapper.class);
        User user2=userMapper2.findUserById(1);
        System.out.println(user2);
        sqlSession2.close();

       /* */
    }

}
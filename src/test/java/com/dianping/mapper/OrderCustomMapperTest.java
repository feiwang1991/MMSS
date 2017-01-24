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

}
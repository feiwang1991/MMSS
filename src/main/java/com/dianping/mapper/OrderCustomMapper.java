package com.dianping.mapper;

import com.dianping.po.Orders;
import com.dianping.po.User;
import com.dianping.pojo.OrdersCustom;

import java.util.List;

/**
 * created by IntelliJ IDEA
 * mapper接口，用于查询有订单的订单信息和用户信息
 */
public interface OrderCustomMapper {
    //查询订单（主）和对应购买用户的信息 resultType
    public List<OrdersCustom> findOrderCustom() throws Exception;

    //查询订单（主）和对应购买用户的信息 resultMap
    public List<Orders> findOrderCustomResultMap() throws Exception;

    //查询订单（主）及订单明细信息
    public List<Orders> findOrderAndOrderDetail() throws Exception;

    //查询用户购买的商品信息
    public List<User> findUserAndItems() throws Exception;
}

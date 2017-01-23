package com.dianping.pojo;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.Orders;

/**
 *订单扩展类，pojo
 * 此类包含订单和用户关联查询的结果，专门写一个拓展类进行封装，用于封装关联查询的结果
 *author：王斐
 *create：2017-01-23  22:22
 */

public class OrdersCustom extends Orders {
    //添加 用户 username,sex,address
    private String username;
    private String sex;
    private String address;

    @Override
    public String toString() {
        return "OrdersCustom{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

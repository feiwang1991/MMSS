package com.dianping.po;/**
 * created by IntelliJ IDEA
 */

import java.util.Date;
import java.util.List;

/**
 *用户订单类，PO
 *author：王斐
 *create：2017-01-23  22:18
 */

public class Orders {
    private Integer id;
    private Integer userId;
    private String number;
    private Date createtime;
    private String note;
    //在一对一关联查询，使用resultMap方式的pojo形式时需要关联从表对象
    private User user;
    //在一对多关联查询中，使用resultMap方式的pojo形式，关联从表对象
    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

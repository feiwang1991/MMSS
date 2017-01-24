package com.dianping.po;/**
 * created by IntelliJ IDEA
 */

import java.util.Date;

/**
 *商品po
 *author：王斐
 *create：2017-01-24  14:34
 */

public class Item {
    private Integer id;
    private String name;
    private Float price;
    private String detail;
    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}

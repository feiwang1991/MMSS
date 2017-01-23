package com.dianping.vo;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.UserCustom;

import java.util.List;

/**
 *视图类，用于包装从视图层传输到持久层的用户查询条件
 *author：王斐
 *create：2017-01-23  10:53
 */

public class UserQueryVo {
    //用于包装所有用户的复杂查询条件
    private UserCustom userCustom;


    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    //还可以加上其他内容，如订单，商品等
    private List<Integer> ids;

    public UserCustom getUserCustom() {
        return userCustom;
    }

    public void setUserCustom(UserCustom userCustom) {
        this.userCustom = userCustom;
    }



}

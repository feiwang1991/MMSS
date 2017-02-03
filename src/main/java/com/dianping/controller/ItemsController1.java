package com.dianping.controller;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.Items;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *商品的Handler
 *author：王斐
 *create：2017-02-03  15:42
 */

public class ItemsController1 implements Controller {
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        //这个类似struts2的action,下面需要调用service,此处我们模拟一个Service调用
        List<Items> itemsList=new ArrayList<Items>();
        Items i1 = new Items();
        i1.setName("手机");
        i1.setId(1);
        i1.setPrice(1000f);
        i1.setCreatetime(new Date());
        itemsList.add(i1);
        Items i2 = new Items();
        i2.setName("索尼电脑");
        i2.setId(2);
        i2.setPrice(20000f);
        i2.setCreatetime(new Date());
        itemsList.add(i2);
        ModelAndView modelAndView = new ModelAndView();
        //这个方法类似request中的setAttrabute()
        modelAndView.addObject("itemsList",itemsList);
        modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        return modelAndView;
    }
}

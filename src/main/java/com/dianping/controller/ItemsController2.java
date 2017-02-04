package com.dianping.controller;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.Items;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *商品的Hander,实现HttpRequestHandler接口
 *类似传统的Httrequest
 *author：王斐
 *create：2017-02-04  10:31
 */

public class ItemsController2 implements HttpRequestHandler{
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
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
        //这个方法就是用传统的方式，传递model数据和view jsp
        httpServletRequest.setAttribute("itemsList",itemsList);
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(httpServletRequest,httpServletResponse);
        //使用此方式可以通过response修改响应数据格式，比如相应json数据
        /*httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write("json串");*/
    }
}

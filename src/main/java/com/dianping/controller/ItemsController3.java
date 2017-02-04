package com.dianping.controller;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.Items;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品的Hander,注解方式的处理器
 * 类似传统的Httrequest
 * author：王斐
 * create：2017-02-04  10:31
 */
//使用注解方式标识该类是一个处理器，比以前的实现controller接口，
// 可以写多个不同方法，而非注解实现接口方法，只能有一个方法
@Controller
public class ItemsController3 {
    //进行处理器映射器的工作，注解方式进行url映射
    //最好url和方法名称是一样的,注意这里/queryItems可以不加.action,但是浏览器url中必须加上（如果设置为*.action的话）
    @RequestMapping("/queryItems")
    public ModelAndView queryItems() throws Exception {
        List<Items> itemsList = new ArrayList<Items>();
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
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        return modelAndView;

    }

}

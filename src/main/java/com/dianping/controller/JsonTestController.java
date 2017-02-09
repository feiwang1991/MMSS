package com.dianping.controller;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.ItemsCustom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *对json进行测试
 *author：王斐
 *create：2017-02-09  17:02
 */
@Controller
public class JsonTestController {

    //1 前端通过ajax传输json数据到controller,然后controller返回json数据，注意因为是ajax请求，故不需要返回具体url，直接原路返回
    //因此可以直接返回json对象
    //@RequestBody把传进来的json形式的参数，转换成java对象
    //@ResponseBody把java对象转换成json形式的数据，发送回去
    @RequestMapping("/requestBody")
    public @ResponseBody ItemsCustom requestBody(@RequestBody ItemsCustom itemsCustom) throws Exception{

        return itemsCustom;
    }
    @RequestMapping("/responseBody")
    //这里传输的参数就是普通形式的key-value参数，不需要进行json转换，springmvc自动转换成java对象，但是一般输出还要把java对象转换成json形式参数
    public @ResponseBody ItemsCustom responseBody(ItemsCustom itemsCustom) throws Exception {
        return itemsCustom;
    }

}

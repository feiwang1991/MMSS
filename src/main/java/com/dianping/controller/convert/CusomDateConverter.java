package com.dianping.controller.convert;/**
 * created by IntelliJ IDEA
 */

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *author：王斐
 *create：2017-02-07  1:08
 */

public class CusomDateConverter implements Converter<String,Date>{

    public Date convert(String source) {
        //把yyyy-MM-dd HH:mm:ss这个格式传入的时间字符串，转换成springmvc和数据库可以识别的java.util.Date
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

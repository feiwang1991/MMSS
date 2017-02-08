package com.dianping.exception;/**
 * created by IntelliJ IDEA
 */

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *自定义的springMVC全局异常处理类
 *author：王斐
 *create：2017-02-08  22:34
 */

public class CustomExceptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /*全局异常处理器的处理思路：
        判断异常的类型，看是否是我们自定义的异常类型
        如果该异常是自定义异常，那么直接取出异常信息，在错误页面 进行显示
        如果该异常不是自定义异常，基本就是运行时异常，那么此时构造一个自定义异常对象，信息为“未知错误”*/
        CustomException customException=null ;
        if(ex instanceof  CustomException){
            customException= (CustomException) ex;
        }
        else
            customException=new CustomException("未知错误");
        String message=customException.getMessage();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message",message);
        modelAndView.setViewName("error");
        return modelAndView;
    }
}

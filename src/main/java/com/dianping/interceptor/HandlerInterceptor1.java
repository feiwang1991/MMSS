package com.dianping.interceptor;/**
 * created by IntelliJ IDEA
 */

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *处理器拦截器
 *author：王斐
 *create：2017-02-11  18:58
 */

public class HandlerInterceptor1 implements HandlerInterceptor{
    //在进入处理器handler方法之前调用此方法
    //使用场景：身份校验
    //return true表示放行，false表示拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }
    //在进入处理器方法之后，在返回modelandview之前调用此方法
    //使用场景：统一设置公共的model数据；设置统一的视图
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    //在调用handler方法之后调用此方法
    //使用场景：统一的异常处理或者根据是否有异常（ex是否为空）进行日志处理
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

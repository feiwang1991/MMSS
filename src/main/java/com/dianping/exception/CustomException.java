package com.dianping.exception;/**
 * created by IntelliJ IDEA
 */

/**
 *自定义异常,其他异常继承于这个异常
 *author：王斐
 *create：2017-02-08  22:30
 */

public class CustomException extends Exception {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public CustomException(String message){
        super(message);
        this.message=message;
    }

}

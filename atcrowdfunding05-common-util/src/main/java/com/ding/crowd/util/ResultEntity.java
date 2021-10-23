package com.ding.crowd.util;

import com.sun.jndi.dns.DnsUrl;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-22-22:02
 * @since JDK 1.8
 *
 *  统一整个项目中Ajax请求返回的结果（未来有可以用于分布式架构各个模块间调用时返回统一类型）
 */


public class ResultEntity<T> {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";

    // 封装当前请求处理的结果是成功还是失败
    private String result;
    // 请求处理失败时返回的错误消息
    private String message;
    // 要返回的数据
    private T data;

    /**
     * 请求处理成功且不需要返回数据时使用的工具方法
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData() {
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    /**
     * 请求成功且需要返回数据时使用的工具方法
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<Type>(SUCCESS, null, data);
    }

    /**
     * 请求处理失败后使用的工具方法
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message) {
        return new ResultEntity<Type>(FAILED, message,null);
    }


    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getFAILED() {
        return FAILED;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}



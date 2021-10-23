package com.ding.crowd.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Qidong Ding
 * @description TODO：判断请求类型
 * @date 2021-10-23-12:53
 * @since JDK 1.8
 */

public class CrowdUtil {


    /**
     * 判断当前请求是否是Ajax请求
     * @param request 请求对象
     * @return true：是Ajax false：不是Ajax
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1、获取请求消息头
        String accept = request.getHeader("Accept");
        String xrequestHeader = request.getHeader("X-Requested-With");

        // 2、判断
        return (accept != null && accept.contains("application/json")) || xrequestHeader != null && xrequestHeader.contains("XMLHttpRequest");
    }

}

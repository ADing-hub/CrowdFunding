package com.ding.crowd.util;

import com.ding.crowd.constant.CrowdContant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Qidong Ding
 * @description TODO：统用工具类
 * @date 2021-10-23-12:53
 * @since JDK 1.8
 */

public class CrowdUtil {

    /**
     * 字符串MD5加密方法
     * @param source 源数据
     * @return 返回md5加密后数据
     */
    public static String md5(String source) {
        // 1、判断source是否有效
        if (source.isEmpty()) {
            // 2、无效的字符串将跑出异常
            throw new RuntimeException(CrowdContant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            // 3、获取MesssageDigest对象
            String algorinthm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorinthm);
            // 4、获取明文字符串对应的数组
            byte[] input = source.getBytes();
            // 5、对字节数组加密
            byte[] digestOut = messageDigest.digest(input);
            // 6、创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, digestOut);
            // 7、按照16进行将bigInteger的值转换为字符串
            int radix = 16;
            String encode = bigInteger.toString(radix).toUpperCase();
            return encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


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

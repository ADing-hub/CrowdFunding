package com.ding.crowd.mvc.interceptor;

import com.ding.crowd.constant.CrowdContant;
import com.ding.crowd.entity.Admin;
import com.ding.crowd.util.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-23-19:07
 * @since JDK 1.8
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1、通过request对象获取Session对象
        HttpSession session = request.getSession();
        // 2、尝试从Session中获取admin对象
        Admin admin = (Admin) session.getAttribute(CrowdContant.ATTR_NAME_LOGI_ADMIN);
        // 3、判断admin对象是否为null
        if (admin == null) {
            // 4、抛出异常
            throw new AccessForbiddenException(CrowdContant.MESSAGE_ACCESS_FORBIDDEN);
        }
        // 5、如果admin对象不为null，则返回true放行
        return true;
    }

    /**
     * This implementation is empty.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}

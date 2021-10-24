package com.ding.crowd.mvc.config;

import com.ding.crowd.constant.CrowdContant;
import com.ding.crowd.util.CrowdUtil;
import com.ding.crowd.util.ResultEntity;
import com.ding.crowd.util.exception.AccessForbiddenException;
import com.ding.crowd.util.exception.LoginAcctAlreadyInUserException;
import com.ding.crowd.util.exception.LoginAcctAlreadyInUserForUpdateException;
import com.ding.crowd.util.exception.LoginFailedException;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-23-13:10
 * @since JDK 1.8
 */

// ControllerAdvice ：表示当前这个类是一个基于注解的异常处理器类
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 帐号修改异常
     * @param exception 实际捕获的异常类型
     * @param request 当前请求对象
     * @param response 当前相应请求
     * @return 返回页面
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUserForUpdateException.class)
    public ModelAndView LoginAcctAlreadyInUserForUpdateException(LoginAcctAlreadyInUserForUpdateException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 帐号添加异常
     * @param exception 实际捕获的异常类型
     * @param request 当前请求对象
     * @param response 当前相应请求
     * @return 返回页面
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUserException.class)
    public ModelAndView LoginAcctAlreadyInUserException(LoginAcctAlreadyInUserException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 登录异常
     * @param exception 实际捕获的异常类型
     * @param request 当前请求对象
     * @param response 当前相应请求
     * @return 返回页面
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView AccessForbiddenException(AccessForbiddenException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 登录异常
     * @param exception 实际捕获的异常类型
     * @param request 当前请求对象
     * @param response 当前相应请求
     * @return 返回页面
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveMathException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 算术异常
     * @param exception 实际捕获的异常类型
     * @param request 当前请求对象
     * @param response 当前相应请求
     * @return
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException(ArithmeticException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 空指针异常
     * @param exception 实际捕获的异常类型
     * @param request 当前请求对象
     * @param response 当前相应请求
     * @return
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPoiterException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 抽取的公共方法
     * @param viewName 转发的页面
     * @param e 捕获的异常
     * @param request 当前请求的对象
     * @param response 当前响应的对象
     * @return 返回
     * @throws IOException
     * @ExceptionHandler：将一个具体的异常类型和一个方法关联起来
     */
    private ModelAndView commonResolve(String viewName, Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1、判断当前请求类型
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        // 2、如果是Ajax请求
        if (judgeRequestType) {
            // 3、创建ResultEntity对象
            ResultEntity<Object> failed = ResultEntity.failed(e.getMessage());
            // 4、创建Gson对象
            Gson gson = new Gson();
            // 5、将ResultEntity对象转换为JSON字符串
            String json = gson.toJson(failed);
            // 6、将JSON字符串作为响应体返回浏览器
            response.getWriter().write(json);
            // 7、由于上面已经通过原声response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        // 8、如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        // 9、将Exception对象存入模型
        modelAndView.addObject(CrowdContant.ATTR_NAME_EXCEPTION, e);
        // 10、设置对象的视图
        modelAndView.setViewName(viewName);
        // 11、返回ModelAndView对象
        return modelAndView;
    }
}

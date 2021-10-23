package com.ding.crowd.mvc.handler;

import com.ding.crowd.constant.CrowdContant;
import com.ding.crowd.entity.Admin;
import com.ding.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-23-16:13
 * @since JDK 1.8
 */

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/do/doLogin.html")
    public String doLogin(@RequestParam(value = "username") String userName, @RequestParam(value = "userpwd") String userPwd, HttpSession session) {
        // 调用Service方法进行登录检查
        // 如果正确返回admin对象则登录成功，如果帐号、密码错误则跑出LoginFailedException异常
        Admin admin = adminService.getAdminByLoginAcct(userName, userPwd);
        // 将登录成功返回的对象，存入Session域中
        session.setAttribute(CrowdContant.ATTR_NAME_LOGI_ADMIN, admin);
        return "redirect:/admin/to/main.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/do/login.html";
    }

    @RequestMapping("/admin/get/user.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize, ModelMap modelMap) {
        // 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        // 添加到ModelMap中
        modelMap.addAttribute(CrowdContant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

}

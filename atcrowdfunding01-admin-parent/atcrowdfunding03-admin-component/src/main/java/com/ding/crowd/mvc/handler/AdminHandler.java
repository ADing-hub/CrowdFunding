package com.ding.crowd.mvc.handler;

import com.ding.crowd.constant.CrowdContant;
import com.ding.crowd.entity.Admin;
import com.ding.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
        session.setAttribute(CrowdContant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/do/login.html";
    }

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, ModelMap modelMap) {
        // 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        // 添加到ModelMap中
        modelMap.addAttribute(CrowdContant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") Integer pageNum, @PathVariable("keyword") String keyword) {
        // 调用Service 删除
        adminService.remove(adminId);

        // 页面跳转，回到分页页面
        // 尝试方案1：直接转发到 admin-page.jsp 会无法显示分页数据
        // return "admin-page";

        // 尝试方案2：转发到/admin/get/page.html地址,一旦刷新页面就会重新执行删除浪费性能
        // return "forward:/admin/get/page.html";

        // 尝试方案3：重定向到/admin/get/page.html 地址
        // 同时为了保持原本所在的页面和查询关键词在附加 pageNum和keyword 两个请求参数
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword") String keyword,
                             ModelMap modelMap) {
        // 1、根据id查询admin对象
        Admin admin = adminService.getAdminById(adminId);
        // 2、将admin对象存入模型
        modelMap.addAttribute(CrowdContant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "admin-edit";
    }

    @RequestMapping("/admin/update.html")
    public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}

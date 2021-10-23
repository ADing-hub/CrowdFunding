package com.ding.crowd.service.api;

import com.ding.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-22-16:04
 * @since JDK 1.8
 */

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    /**
     * 根据帐号密码进行admin登录
     * @param loginAcct 用户名
     * @param userPwd 密码
     * @return 返回admin对象
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPwd);

    /**
     *
     * @param keyword 查询关键字
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @return
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
}

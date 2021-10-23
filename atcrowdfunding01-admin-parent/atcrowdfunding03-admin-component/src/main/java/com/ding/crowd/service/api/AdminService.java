package com.ding.crowd.service.api;

import com.ding.crowd.entity.Admin;

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
}

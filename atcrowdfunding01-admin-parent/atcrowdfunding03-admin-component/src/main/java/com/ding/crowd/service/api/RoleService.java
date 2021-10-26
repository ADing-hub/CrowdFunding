package com.ding.crowd.service.api;

import com.ding.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-24-21:48
 * @since JDK 1.8
 */

public interface RoleService {

    /**
     * 分页查询
     * @param pageNum 当前分页
     * @param PageSize 分页数量
     * @param keyword 关键字
     * @return
     */
    PageInfo<Role> getPageInfo(Integer pageNum, Integer PageSize, String keyword);

    /**
     * 保存角色
     * @param role 角色
     */
    void savaRole(Role role);

    /**
     * 更新角色
     * @param role 更新角色
     */
    void update(Role role);

    /**
     * 批量删除
     * @param roleList 批量数组
     */
    void removeRole(List<Integer> roleList);
}

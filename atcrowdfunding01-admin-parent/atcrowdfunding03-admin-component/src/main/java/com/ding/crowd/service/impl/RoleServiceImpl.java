package com.ding.crowd.service.impl;

import com.ding.crowd.entity.Role;
import com.ding.crowd.entity.RoleExample;
import com.ding.crowd.mapper.RoleMapper;
import com.ding.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-24-21:48
 * @since JDK 1.8
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 分页查询
     * @param pageNum 当前分页
     * @param PageSize 分页数量
     * @param keyword 关键字
     * @return 返回PageInfo对象
     */
    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer PageSize, String keyword) {

        // 1、开启分页功能
        PageHelper.startPage(pageNum, PageSize);
        // 2、执行查询
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);
        // 3、封装为PageInfo对象返回
        return new PageInfo<>(roles);
    }

    /**
     * 保存角色
     * @param role 角色
     */
    @Override
    public void savaRole(Role role) {
        roleMapper.insert(role);
    }

    /**
     * 更新角色
     *
     * @param role 更新角色
     */
    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }


    /**
     * 批量删除
     *
     * @param roleList 批量数组
     */
    @Override
    public void removeRole(List<Integer> roleList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleList);
        roleMapper.deleteByExample(roleExample);
    }
}

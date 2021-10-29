package com.ding.crowd.service.impl;

import com.ding.crowd.entity.Auth;
import com.ding.crowd.entity.AuthExample;
import com.ding.crowd.mapper.AuthMapper;
import com.ding.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-26-20:26
 * @since JDK 1.8
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.getAssignedAuthIdByRoleId(roleId);
    }

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public void savaRoleAuthRelathinShip(Map<String, List<Integer>> map) {
        // 获取RoleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer  roleId= roleIdList.get(0);
        // 删除旧的数据
        authMapper.deleteOldRelationshipByRoleId(roleId);
        // 获取AuthIdList
        List<Integer> authIdList = map.get("authIdList");

        // 判断authIdList是否有效
        if (authIdList != null || authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByRoleId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByRoleId(adminId);
    }
}

package com.ding.crowd.service.api;

import com.ding.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-26-20:26
 * @since JDK 1.8
 */

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void savaRoleAuthRelathinShip(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByRoleId(Integer adminId);


}

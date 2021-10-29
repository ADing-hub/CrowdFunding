package com.ding.crowd.mvc.config;

import com.ding.crowd.entity.Admin;
import com.ding.crowd.entity.Role;
import com.ding.crowd.service.api.AdminService;
import com.ding.crowd.service.api.AuthService;
import com.ding.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-29-13:55
 * @since JDK 1.8
 */

@Component
public class CorwdUserdetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据帐号名称查询ADMIN对象
        Admin admin = adminService.getAdminByLoginAcct(s);
        // 获取admin的id
        Integer id = admin.getId();
        // 根据id查询角色信息
        List<Role> roleList = roleService.getAssignedRole(id);
        // 根据id查询权限信息
        List<String> authNameByRoleId = authService.getAssignedAuthNameByRoleId(id);
        // 创建集合的对象用来存储GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // 遍历roleList 存入角色信息
        for (Role role : roleList) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        
        // 遍历authNameByRoleId 存入权限信息
        for (String authName : authNameByRoleId) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        // 封装 SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        return securityAdmin;
    }
}

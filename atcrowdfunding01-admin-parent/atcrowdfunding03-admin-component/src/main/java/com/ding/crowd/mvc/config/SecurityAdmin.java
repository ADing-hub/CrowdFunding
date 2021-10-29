package com.ding.crowd.mvc.config;

import com.ding.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-29-12:31
 * @since JDK 1.8
 * 考虑到User对象中渐渐包含帐号和密码，为了能够获取到原始的admin对象，专门创建这个类对user类进行扩展
 */

public class SecurityAdmin extends User {

    // 原始的admin，包含Admin对象的全部属性
    private Admin orgadmin;

    /**
     * @param orgadmin    传入原始的admin对象
     * @param authorities 创建权限和角色的信息集合
     */
    public SecurityAdmin(Admin orgadmin, List<GrantedAuthority> authorities) {
        // 调用父类构造器
        super(orgadmin.getUserName(), orgadmin.getUserPswd(), authorities);
        // 给原始admin赋值
        this.orgadmin = orgadmin;
        this.orgadmin.setUserPswd(null);

    }

    // 对外提供获取原始Admin对象的get方法
    public Admin getOrgadmin() {
        return orgadmin;
    }

}

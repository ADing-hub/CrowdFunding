package com.ding.crowd.mvc.handler;

import com.ding.crowd.entity.Auth;
import com.ding.crowd.entity.Role;
import com.ding.crowd.service.api.AdminService;
import com.ding.crowd.service.api.AuthService;
import com.ding.crowd.service.api.RoleService;
import com.ding.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-26-17:41
 * @since JDK 1.8
 */

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("assign/to/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        // 查询已经分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        // 存入模型
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }

    @RequestMapping("assign/do/assign.html")
    public String saveAdminRoleRlationship(@RequestParam("adminId") Integer adminId,
                                           @RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("keyword") String keyword,
                                           @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {

        adminService.saveAdminRoleRlationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @ResponseBody
    @RequestMapping("assign/get/tree.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("assign/get/checked/auth/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
        List<Integer> authList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("assign/do/save/role/auth/relationship.json")
    public ResultEntity<String> savaRoleAuthRelathinShip(@RequestBody Map<String, List<Integer>> map) {
        authService.savaRoleAuthRelathinShip(map);
        return ResultEntity.successWithoutData();
    }
}

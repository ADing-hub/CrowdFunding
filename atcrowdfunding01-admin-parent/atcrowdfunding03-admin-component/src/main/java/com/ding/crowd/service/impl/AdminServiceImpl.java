package com.ding.crowd.service.impl;

import com.ding.crowd.constant.CrowdContant;
import com.ding.crowd.entity.Admin;
import com.ding.crowd.entity.AdminExample;
import com.ding.crowd.entity.Role;
import com.ding.crowd.mapper.AdminMapper;
import com.ding.crowd.service.api.AdminService;
import com.ding.crowd.util.CrowdUtil;
import com.ding.crowd.util.exception.LoginAcctAlreadyInUserException;
import com.ding.crowd.util.exception.LoginAcctAlreadyInUserForUpdateException;
import com.ding.crowd.util.exception.LoginFailedException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-22-16:05
 * @since JDK 1.8
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

     @Autowired
     private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 保存admin对象@Override
     *
     * @param admin admin对象
     */
    @Override
    public void saveAdmin(Admin admin) {
        // 1、密码加密
//        String md5 = CrowdUtil.md5(admin.getUserPswd());
//        admin.setUserPswd(md5);
        String userPswd = admin.getUserPswd();
        String encode = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(encode);
        // 2、生成创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        admin.setCreateTime(format);
        // 3、执行保存
        try {
            adminMapper.insert(admin);
        } catch (
                Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUserException(CrowdContant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    /**
     * 根据帐号密码进行admin登录
     *
     * @param loginAcct 用户名
     * @param userPwd   密码
     * @return 返回admin对象
     */
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPwd) {
        // 1、根据登录帐号进行查询
        // ① 创建Example对象
        AdminExample adminExample = new AdminExample();
        // ② 创建Criteria对象
        AdminExample.Criteria adminExampleCriteria = adminExample.createCriteria();
        // ③ 封装查询条件
        adminExampleCriteria.andLoginAcctEqualTo(loginAcct);
        // ④ 调用Mapper方法进行查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        // 2、判断admin是否为null,以及数据库中帐号不唯一的情况
        if (admins == null || admins.size() == 0) {
            throw new LoginFailedException(CrowdContant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1) {
            throw new RuntimeException(CrowdContant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = admins.get(0);
        // 3、如果admin为null，则抛出异常
        if (admin == null) {
            throw new RuntimeException(CrowdContant.MESSAGE_LOGIN_FAILED);
        }
        // 4、如果admin不为null，则将admin中的密码从数据库取出
        String userPswdDB = admin.getUserPswd();
        // 5、将表单提交的明文密码进行加密
        String userPwdMd5Form = CrowdUtil.md5(userPwd);
        // 6、比对数据库中的密码
        if (!Objects.equals(userPswdDB, userPwdMd5Form)) {
            // 7、如果比较结果不一致，则抛出异常
            throw new RuntimeException(CrowdContant.MESSAGE_LOGIN_FAILED);
        }
        // 8、如果比较结果一直，则返回admin对象
        return admin;
    }

    /**
     * @param keyword  查询关键字
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @return
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1、调用PageHelper的静态方法开启分页功能
        // 充分体现了PageHelper的“非入侵式”设计：原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum, pageSize);
        // 2、执行查询
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);
        // 3、封装到PageInfo中
        return new PageInfo<>(admins);
    }

    /**
     * 删除用户
     *
     * @param adminId 用户Id
     */
    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    /**
     * 根据adminId查询admin对象
     *
     * @param adminId adminid
     * @return
     */
    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 更新admin对象
     *
     * @param admin admin对象
     */
    @Override
    public void update(Admin admin) {
        // 有选择的更新，对于null值不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUserForUpdateException(CrowdContant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

    }

    @Override
    public void saveAdminRoleRlationship(Integer adminId, List<Integer> roleIdList) {
        adminMapper.deleteOldRelationship(adminId);
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.saveNewRelationship(adminId,roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String s) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(s);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        Admin admin = admins.get(0);
        return admin;
    }
}

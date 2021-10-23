package com.ding.crowd.service.impl;

import com.ding.crowd.entity.Admin;
import com.ding.crowd.entity.AdminExample;
import com.ding.crowd.mapper.AdminMapper;
import com.ding.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
       return adminMapper.selectByExample(new AdminExample());
    }

}

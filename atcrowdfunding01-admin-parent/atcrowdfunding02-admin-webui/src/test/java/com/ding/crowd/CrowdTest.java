package com.ding.crowd;

import com.ding.crowd.entity.Admin;
import com.ding.crowd.entity.Role;
import com.ding.crowd.mapper.AdminMapper;
import com.ding.crowd.mapper.RoleMapper;
import com.ding.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-21-21:44
 * @since JDK 1.8
 */

// 在类上标记必要的注解。Spring整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-persist-mybatis.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRoleSave() {
        for (int i = 1; i < 232; i++) {
            Role role = new Role(null, "role" + i);
            roleMapper.insert(role);
        }
    }

    @Test
    public void test() {
        for (int i = 1; i < 232; i++) {
            Admin admin = new Admin(null, "jerry"+i, "userPwd"+i, "userName"+i, "email"+i, null);
            adminService.saveAdmin(admin);
        }
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
        throw new RuntimeException();
    }

    @Test
    public void testLog() {
        // 1. 获取logger对象
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        //2. 根据不同的日志级别
        logger.debug("Debug");
        logger.debug("Debug");
        logger.debug("Debug");

        logger.info("info");
        logger.info("info");
        logger.info("info");

        logger.warn("warning");
        logger.warn("warning");
        logger.warn("warning");

        logger.error("error");
        logger.error("error");
        logger.error("error");
    }

    /**
     * 测试Mapper
     */
    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tangmu@qq.com", null);
        int insert = adminMapper.insert(admin);

        System.out.println(insert);
    }

    /**
     * 测试数据源
     * @throws SQLException
     */
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}

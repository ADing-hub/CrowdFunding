package com.ding.crowd.mvc.handler;

import com.ding.crowd.entity.Admin;
import com.ding.crowd.entity.Student;
import com.ding.crowd.service.api.AdminService;
import com.ding.crowd.util.CrowdUtil;
import com.ding.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-22-19:50
 * @since JDK 1.8
 */

@Controller
public class TestHandler {

    private static Logger logger = LoggerFactory.getLogger(TestHandler.class);


    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("/send/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array){
        for (Integer num : array) {
            System.out.println(num);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/two.html")
    public String testReceiveArrayTwo(@RequestBody() String arraystr){

        logger.info(arraystr);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student,HttpServletRequest request){
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("是否Ajax：" + b);
        logger.info(student.toString());
        // 将查询到的student对象封装到ResultEntity返回
        ResultEntity<Student> studentResultEntity = ResultEntity.successWithData(student);
        return studentResultEntity;
    }

    @RequestMapping("/test/ssm.html")
    public String testSsm(Model model, HttpServletRequest request) {
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("是否Ajax：" + b);
        List<Admin> adminList = adminService.getAll();
        model.addAttribute("adminlist",adminList);
        int i = 10 / 0;
        return "target";
    }
}

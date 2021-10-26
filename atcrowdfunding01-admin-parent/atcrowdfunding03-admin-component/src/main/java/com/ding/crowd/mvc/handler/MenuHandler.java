package com.ding.crowd.mvc.handler;

import com.ding.crowd.entity.Menu;
import com.ding.crowd.service.api.MenuService;
import com.ding.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-25-21:45
 * @since JDK 1.8
 */

@Controller
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/do/get.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        // 查出所有的Menu对象
        List<Menu> menuList = menuService.getAll();
        // 声明一个变量用来存储找到的根节点
        Menu root = null;
        // 创建Map对象来存储id和Menu对象的对应关系便于查找父节点
        HashMap<Integer, Menu> menuHashMapp = new HashMap<>();

        // 遍历menuList 查找根节点，组装父节点
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuHashMapp.put(id, menu);
        }
        // 再次遍历MenuList 查找根节点，组装父子节点
        for (Menu menu : menuList) {
            // 获取当前Menu对象的pid
            Integer pid = menu.getPid();
            // 如果pid等于null，判断为根节点
            if (pid == null) {
                root = menu;
                continue;
            }
            // 如果b不为null ,说明当前节点有父节点，那么可以根据pid查找对应的Menu对象
            Menu father = menuHashMapp.get(pid);
            // 将当前节点存入父节点
            father.getChildren().add(menu);

        }
        return ResultEntity.successWithData(root);
    }

    public ResultEntity<Menu> getWholeTreeOld() {
        // 查出所有的Menu对象
        List<Menu> menuList = menuService.getAll();
        // 声明一个变量用来存储找到的根节点
        Menu root = null;
        // 遍历menuList
        for (Menu menu : menuList) {
            // 获取当前Menu对象pid属性值
            Integer pid = menu.getPid();
            // 检查pid是否为null
            if (pid == null) {
                // 把当前正在遍历的这个Menu对象赋值给root
                root = menu;
                // 停止本次循环，继续执行下一次循环
                continue;
            }
            // 如果pid不为null，说明当前节点有父节点，找到父节点就可以组装，建立父子关系
            for (Menu mybeFather : menuList) {
                // 获取nybeFather 的id属性
                Integer id = mybeFather.getId();
                // 将子节点的pid和疑似父节点的id进行比较
                if (Objects.equals(pid, id)) {
                    // 将子节点存入父节点的children集合
                    mybeFather.getChildren().add(menu);
                    // 找到即可停止运行循环
                    break;
                }
            }
        }
        // 将组装好的树形结构，返回给浏览器
        return ResultEntity.successWithData(root);
    }
}

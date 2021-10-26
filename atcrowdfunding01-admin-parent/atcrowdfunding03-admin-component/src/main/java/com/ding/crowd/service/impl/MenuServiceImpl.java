package com.ding.crowd.service.impl;

import com.ding.crowd.entity.Menu;
import com.ding.crowd.entity.MenuExample;
import com.ding.crowd.mapper.MenuMapper;
import com.ding.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-25-21:44
 * @since JDK 1.8
 */

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }
}

package com.ding.crowd.service.api;

import com.ding.crowd.entity.Menu;

import java.util.List;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-25-21:44
 * @since JDK 1.8
 */

public interface MenuService {

    List<Menu> getAll();

    void save(Menu menu);

    void update(Menu menu);

    void remove(Integer id);
}

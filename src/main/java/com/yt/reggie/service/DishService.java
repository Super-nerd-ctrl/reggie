package com.yt.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yt.reggie.dto.DishDto;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Lenovo
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-02-05 12:18:38
*/
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时保存对应口味的数据
     * @param dishDto
     * @return
     */
    @Transactional
    R<String> saveWithFlavor(DishDto dishDto);

    R<Page> pageSelect(int page, int pageSize, String name);

    R<DishDto> getDishDto(Long id);

    @Transactional
    R<String> updateWithFlavor(DishDto dishDto);
}

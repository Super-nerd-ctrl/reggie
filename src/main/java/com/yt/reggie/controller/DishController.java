package com.yt.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.dto.DishDto;
import com.yt.reggie.service.DishFlavorService;
import com.yt.reggie.service.DishService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> saveDish(@RequestBody DishDto dishDto) {

        return dishService.saveWithFlavor(dishDto);
    }

    @GetMapping("/page")
    public R<Page> pageSelect(int page, int pageSize, String name) {

        return dishService.pageSelect(page, pageSize, name);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishDto(@PathVariable Long id) {

        return dishService.getDishDto(id);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {

        return dishService.updateWithFlavor(dishDto);
    }
}

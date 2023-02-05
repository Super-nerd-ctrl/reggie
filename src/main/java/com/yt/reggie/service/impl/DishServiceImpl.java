package com.yt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Category;
import com.yt.reggie.domain.Dish;
import com.yt.reggie.domain.DishFlavor;
import com.yt.reggie.dto.DishDto;
import com.yt.reggie.service.CategoryService;
import com.yt.reggie.service.DishFlavorService;
import com.yt.reggie.service.DishService;
import com.yt.reggie.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Lenovo
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-02-05 12:18:38
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Lazy
    @Resource
    private CategoryService categoryService;
    @Resource
    private DishFlavorService dishFlavorService;
    @Override
    public R<String> saveWithFlavor(DishDto dishDto) {
        //保存基本的菜品信息到菜品表
        this.save(dishDto);

        //菜品id
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream()
                .map(flavor -> {
                    flavor.setDishId(dishId);
                    return flavor;
                })
                .collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

        return R.success("菜品保存成功");
    }

    @Override
    public R<Page> pageSelect(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        this.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream()
                .map(dish -> {
                    DishDto dishDto = new DishDto();
                    BeanUtils.copyProperties(dish, dishDto);
                    //分类id
                    Long categoryId = dish.getCategoryId();
                    //根据分类id查询分类对象
                    Category category = categoryService.getById(categoryId);
                    if (category != null) {
                        String categoryName = category.getName();
                        dishDto.setCategoryName(categoryName);
                    }
                    return dishDto;
                })
                .collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @Override
    public R<DishDto> getDishDto(Long id) {
        //查询菜品基本信息，从dish查
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        //查询当前菜品的分类信息，从dish_flavor
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);

        return R.success(dishDto);
    }

    @Override
    public R<String> updateWithFlavor(DishDto dishDto) {
        //更新dish表
        this.updateById(dishDto);
        //清理口味表对应数据
        LambdaQueryWrapper<DishFlavor> queryWrap = new LambdaQueryWrapper<>();
        queryWrap.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrap);
        //添加口味表对应数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream()
                .map(flavor -> {
                    flavor.setDishId(dishDto.getId());
                    return flavor;
                })
                .collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
        return R.success("修改成功");
    }
}





package com.yt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.reggie.common.CustomException;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Category;
import com.yt.reggie.domain.Dish;
import com.yt.reggie.domain.Setmeal;
import com.yt.reggie.service.CategoryService;
import com.yt.reggie.mapper.CategoryMapper;
import com.yt.reggie.service.DishService;
import com.yt.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Lenovo
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-02-05 11:21:22
*/
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    private DishService dishService;

    @Resource
    private SetmealService setmealService;
    @Override
    public R<String> saveCategory(Category category) {
        log.info("category:{}", category);
        save(category);
        return R.success("新增分类成功");
    }

    @Override
    public R<Page> pageCategory(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @Override
    public R<String> deleteCategory(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long count = dishService.count(dishLambdaQueryWrapper);

        //查询当前分类已经关联了菜品，如果已经关联，抛出一个业务异常
        if (count > 0) {
            //已经关联了菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
            //已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");

        }

        this.removeById(id);
        return R.success("分类信息删除成功");
    }

    @Override
    public R<String> updateCategory(Category category) {
        this.updateById(category);
        return R.success("修改信息分类成功");
    }
}





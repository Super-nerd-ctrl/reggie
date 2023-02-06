package com.yt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.reggie.common.CustomException;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Category;
import com.yt.reggie.domain.Setmeal;
import com.yt.reggie.domain.SetmealDish;
import com.yt.reggie.dto.SetmealDto;
import com.yt.reggie.service.CategoryService;
import com.yt.reggie.service.SetmealDishService;
import com.yt.reggie.service.SetmealService;
import com.yt.reggie.mapper.SetmealMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Lenovo
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-02-05 12:18:53
*/
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Resource
    private SetmealDishService setmealDishService;

    @Lazy
    @Resource
    private CategoryService categoryService;
    @Override
    public R<Page> getPage(int page, int pageSize, String name) {
        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        this.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream()
                .map(setmeal -> {
                    SetmealDto setmealDto = new SetmealDto();
                    BeanUtils.copyProperties(setmeal, setmealDto);
                    Long categoryId = setmeal.getCategoryId();
                    //根据分类的id查询分类对象
                    Category category = categoryService.getById(categoryId);
                    if (category != null) {
                        String categoryName = category.getName();
                        setmealDto.setCategoryName(categoryName);
                    }
                    return setmealDto;
                })
                .collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @Override
    public R<String> saveSetmeal(SetmealDto setmealDto) {
        log.info(setmealDto.toString());
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream()
                .map(setmealDish -> {
                    setmealDish.setSetmealId(setmealDto.getId());
                    return setmealDish;
                })
                .collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
        return R.success("保存成功");
    }

    @Override
    public R<String> deleteSetmeal(List<Long> ids) {
        //查看套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        long count = this.count(queryWrapper);
        if (count > 0) {
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中");
        }

        //如果可以删除，先删除套餐表中的数据
        this.removeBatchByIds(ids);

        //删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> queryWrap = new LambdaQueryWrapper<>();
        queryWrap.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrap);

        return R.success("删除成功");
    }
}





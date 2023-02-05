package com.yt.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.reggie.domain.Dish;
import com.yt.reggie.service.DishService;
import com.yt.reggie.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-02-05 12:18:38
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

}





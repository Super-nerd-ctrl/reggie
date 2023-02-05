package com.yt.reggie.mapper;

import com.yt.reggie.domain.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2023-02-05 12:18:38
* @Entity com.yt.reggie.domain.Dish
*/
@Repository
public interface DishMapper extends BaseMapper<Dish> {

}





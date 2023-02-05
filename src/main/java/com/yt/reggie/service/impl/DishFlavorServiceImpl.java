package com.yt.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.reggie.domain.DishFlavor;
import com.yt.reggie.service.DishFlavorService;
import com.yt.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-02-05 17:07:39
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}





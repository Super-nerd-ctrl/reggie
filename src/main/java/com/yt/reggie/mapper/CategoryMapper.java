package com.yt.reggie.mapper;

import com.yt.reggie.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-02-05 11:21:22
* @Entity com.yt.reggie.domain.Category
*/
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}





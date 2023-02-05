package com.yt.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-02-05 11:21:22
*/
public interface CategoryService extends IService<Category> {

    /**
     * 新增分类
     * @param category
     * @return
     */
    public R<String> saveCategory(Category category);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public R<Page> pageCategory(int page, int pageSize);

    /**
     * 删除菜品
     * @param id
     * @return
     */
    public R<String> deleteCategory(Long id);

    /**
     * 修改菜品分类信息
     * @param category
     * @return
     */
    public R<String> updateCategory(Category category);

    R<List<Category>> list(Category category);
}

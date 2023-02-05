package com.yt.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Category;
import com.yt.reggie.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> saveCategory(@RequestBody Category category) {

        return categoryService.saveCategory(category);
    }

    @GetMapping("/page")
    public R<Page> pageCategory(int page, int pageSize) {

        return categoryService.pageCategory(page, pageSize);
    }

    @DeleteMapping
    public R<String> deleteCategory(Long id) {

        return categoryService.deleteCategory(id);
    }

    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {

        return categoryService.updateCategory(category);
    }
}

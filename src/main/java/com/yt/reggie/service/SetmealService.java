package com.yt.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.domain.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yt.reggie.dto.SetmealDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-02-05 12:18:53
*/
public interface SetmealService extends IService<Setmeal> {

    R<Page> getPage(int page, int pageSize, String name);

    @Transactional
    R<String> saveSetmeal(SetmealDto setmealDto);

    @Transactional
    R<String> deleteSetmeal(List<Long> ids);
}

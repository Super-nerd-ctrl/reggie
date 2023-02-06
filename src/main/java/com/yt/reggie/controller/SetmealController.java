package com.yt.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yt.reggie.common.R;
import com.yt.reggie.dto.SetmealDto;
import com.yt.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.soap.SAAJResult;
import java.util.List;

/**
 * 套餐管理
 */
@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<Page> getPage(int page, int pageSize, String name) {

        return setmealService.getPage(page, pageSize, name);
    }

    @PostMapping
    public R<String> saveSetmeal(@RequestBody SetmealDto setmealDto) {

        return setmealService.saveSetmeal(setmealDto);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        return setmealService.deleteSetmeal(ids);
    }

}

package com.yt.reggie.dto;

import com.yt.reggie.domain.Setmeal;
import com.yt.reggie.domain.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

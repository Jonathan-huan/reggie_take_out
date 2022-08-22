package com.hzh.reggie.dto;

import com.hzh.reggie.entity.Setmeal;
import com.hzh.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

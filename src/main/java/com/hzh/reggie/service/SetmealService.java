package com.hzh.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzh.reggie.dto.SetmealDto;
import com.hzh.reggie.entity.Setmeal;
import com.hzh.reggie.mapper.SetmealMapper;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDishes(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除和菜品的关联关系
     * @param ids
     */
    public void removeWithDishes(List<Long> ids);
}

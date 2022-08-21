package com.hzh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzh.reggie.entity.Setmeal;
import com.hzh.reggie.mapper.SetmealMapper;
import com.hzh.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

import java.security.Provider;
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}

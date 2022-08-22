package com.hzh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzh.reggie.dto.DishDto;
import com.hzh.reggie.entity.DishFlavor;
import com.hzh.reggie.mapper.DishFlavorMapper;
import com.hzh.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

package com.hzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzh.reggie.common.R;
import com.hzh.reggie.dto.SetmealDto;
import com.hzh.reggie.entity.Category;
import com.hzh.reggie.entity.Setmeal;
import com.hzh.reggie.entity.SetmealDish;
import com.hzh.reggie.service.CategoryService;
import com.hzh.reggie.service.SetmealDishService;
import com.hzh.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo =new Page(page,pageSize);
        Page<SetmealDto> setmealDtoPage=new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);


        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list= records.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long id = setmealDto.getCategoryId();
            Category category = categoryService.getById(id);
            if(category!=null){
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息：{}",setmealDto.toString());
        setmealService.saveWithDishes(setmealDto);
        //清理所有套餐的缓存数据
        Set keys = redisTemplate.keys("setmeal_*");
        redisTemplate.delete(keys);
        return R.success("新增套餐成功");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDishes(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 获取套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        List<Setmeal> list=null;
        String key="setmeal_"+setmeal.getCategoryId()+"_"+setmeal.getStatus();
        list= (List<Setmeal>) redisTemplate.opsForValue().get(key);
        if(list!=null){
            return R.success(list);
        }


        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,1);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        list=setmealService.list(queryWrapper);

        redisTemplate.opsForValue().set(key,list);
        return R.success(list);
    }
}

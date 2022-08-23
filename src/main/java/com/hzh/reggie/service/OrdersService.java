package com.hzh.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzh.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    /**
     *订单提交
     * @param orders
     */
    public void submit(Orders orders);
}

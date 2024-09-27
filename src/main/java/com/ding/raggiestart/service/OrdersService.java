package com.ding.raggiestart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ding.raggiestart.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}

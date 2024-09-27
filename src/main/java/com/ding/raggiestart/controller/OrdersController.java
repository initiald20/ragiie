package com.ding.raggiestart.controller;

import com.ding.raggiestart.common.R;
import com.ding.raggiestart.entity.Orders;
import com.ding.raggiestart.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> save(@RequestBody Orders orders){
        ordersService.submit(orders);
        return null;
    }
}

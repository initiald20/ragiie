package com.ding.raggiestart.controller;

import com.ding.raggiestart.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
}

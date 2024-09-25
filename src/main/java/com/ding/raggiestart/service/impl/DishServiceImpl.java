package com.ding.raggiestart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ding.raggiestart.entity.Dish;
import com.ding.raggiestart.mapper.DishMapper;
import com.ding.raggiestart.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}

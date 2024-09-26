package com.ding.raggiestart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ding.raggiestart.dto.DishDto;
import com.ding.raggiestart.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}

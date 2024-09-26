package com.ding.raggiestart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ding.raggiestart.dto.SetmealDto;
import com.ding.raggiestart.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
}

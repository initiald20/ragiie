package com.ding.raggiestart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ding.raggiestart.common.CustomException;
import com.ding.raggiestart.entity.Category;
import com.ding.raggiestart.entity.Dish;
import com.ding.raggiestart.entity.Setmeal;
import com.ding.raggiestart.mapper.CategoryMapper;
import com.ding.raggiestart.service.CategoryService;
import com.ding.raggiestart.service.DishService;
import com.ding.raggiestart.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int countDish = dishService.count(dishLambdaQueryWrapper);
        if(countDish>0){
            throw new CustomException("存在绑定中的菜品");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int countSetmeal = setmealService.count(setmealLambdaQueryWrapper);
        if(countSetmeal>0){
            throw new CustomException("存在绑定中的套餐");
        }

        super.removeById(id);
    }
}

package com.ding.raggiestart.dto;

import com.ding.raggiestart.entity.Setmeal;
import com.ding.raggiestart.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
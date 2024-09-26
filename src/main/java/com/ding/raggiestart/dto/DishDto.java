package com.ding.raggiestart.dto;

import com.ding.raggiestart.entity.Dish;
import com.ding.raggiestart.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
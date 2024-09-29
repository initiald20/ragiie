package com.ding.raggiestart.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ding.raggiestart.common.R;
import com.ding.raggiestart.dto.DishDto;
import com.ding.raggiestart.entity.Category;
import com.ding.raggiestart.entity.Dish;
import com.ding.raggiestart.entity.DishFlavor;
import com.ding.raggiestart.service.CategoryService;
import com.ding.raggiestart.service.DishFlavorService;
import com.ding.raggiestart.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        String dkey = "dish" + dishDto.getCategoryId();
        redisTemplate.delete(dkey);
        return R.success("新增成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        String dkey = "dish" + dishDto.getCategoryId();
        redisTemplate.delete(dkey);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        String dkey = "dish" + dish.getCategoryId();
        List<DishDto> dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(dkey);

        if(dishDtoList != null){
            return R.success(dishDtoList);
        }
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            LambdaQueryWrapper<DishFlavor> flvqueryMapper = new LambdaQueryWrapper<>();
            flvqueryMapper.eq(DishFlavor::getDishId, item.getId());

            List<DishFlavor> dishFlavorList = dishFlavorService.list(flvqueryMapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(dkey,dishDtoList, 60, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }
}

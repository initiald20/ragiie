package com.ding.raggiestart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ding.raggiestart.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

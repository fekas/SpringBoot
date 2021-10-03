package com.zhong.blog.service;

import com.zhong.blog.vo.CategoryVo;
import com.zhong.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}


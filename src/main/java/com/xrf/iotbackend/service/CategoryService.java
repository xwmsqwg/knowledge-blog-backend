package com.xrf.iotbackend.service;

import com.xrf.iotbackend.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    List<CategoryVO> getAllCategories();

    CategoryVO createCategory(String name, Integer creatorId);

    void deleteCategory(Integer categoryId , Integer creatorId);

}

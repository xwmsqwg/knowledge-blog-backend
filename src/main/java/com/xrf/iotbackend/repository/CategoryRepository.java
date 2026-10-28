package com.xrf.iotbackend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrf.iotbackend.model.entity.Category;
import com.xrf.iotbackend.model.vo.CategoryVO;

import java.util.List;

public interface CategoryRepository extends BaseMapper<Category> {

    List<CategoryVO> selectAllCategories();

}

package com.xrf.iotbackend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.entity.Category;
import com.xrf.iotbackend.model.vo.CategoryVO;
import com.xrf.iotbackend.repository.CategoryRepository;
import com.xrf.iotbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryVO> getAllCategories() {
        return categoryRepository.selectAllCategories();
    }

    @Override
    @Transactional
    public CategoryVO createCategory(String name, Integer creatorId) {
        String returnName = name.trim();
        long count = categoryRepository.selectCount(
                Wrappers.<Category>lambdaQuery().eq(Category::getName, returnName)
        );
        if (count > 0) {
            throw new BusinessException(ErrorCode.CONFLICT, "分类名称已存在");
        }

        LocalDateTime now = LocalDateTime.now();
        Category category = new Category();
        category.setName(returnName);
        category.setCreatorId(creatorId);
        category.setCreateTime(now);
        category.setUpdateTime(now);

        try {
            categoryRepository.insert(category);
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(ErrorCode.CONFLICT, "分类名称已存在");
        }

        return new CategoryVO(
                category.getId(),
                category.getName(),
                0,
                category.getCreatorId(),
                category.getCreateTime()
        );
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId, Integer creatorId) {
        Category category = categoryRepository.selectById(categoryId);
        if(category == null){
            throw new  BusinessException(
                    ErrorCode.NOT_FOUND,
                    "分类不存在"
            );
        }
        if(!(category.getCreatorId().equals(creatorId))) {
            throw new BusinessException(
                    ErrorCode.FORBIDDEN,
                    "无权删除该分类"
            );
        }
        categoryRepository.deleteById(categoryId);
    }
}

package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.CategoryCreateRequest;
import com.xrf.iotbackend.model.entity.Category;
import com.xrf.iotbackend.model.vo.CategoryVO;
import com.xrf.iotbackend.repository.ArticleRepository;
import com.xrf.iotbackend.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    // 分类已存在时不能再次被新建
    @Test
    void createCategory_categoryIsExist_throwCONFLICT(){
        when(categoryRepository.selectCount(any())).thenReturn(1L);

        BusinessException exception = assertThrows(BusinessException.class , ()->categoryService.createCategory("JAVA" , 8 ));

        assertEquals(ErrorCode.CONFLICT , exception.getErrorCode());

        verify(categoryRepository , never()).insert(any(Category.class));
    }

    // 分类不存在，创建成功
    @Test
    void createCategory_categoryNotExist_success(){
        when(categoryRepository.selectCount(any())).thenReturn(0L);

        CategoryVO result = categoryService.createCategory("java" , 8);

        assertEquals("java" , result.getName());
        assertEquals(8, result.getCreatorId());
        assertEquals(0, result.getArticleCount());
        assertNotNull(result.getCreateTime());

        verify(categoryRepository).insert(any(Category.class));
    }

    // 分类创建时出现唯一键冲突
    @Test
    void createCategory_insertDuplicateKey_throwsConflict(){
        when(categoryRepository.selectCount(any())).thenReturn(0L);
        when(categoryRepository.insert(any(Category.class))).thenThrow(new DuplicateKeyException("重复的分类名"));

        BusinessException exception = assertThrows(BusinessException.class , () -> categoryService.createCategory("java" , 8));

        assertEquals(ErrorCode.CONFLICT , exception.getErrorCode());

        verify(categoryRepository).insert(any(Category.class));
    }

    // 分类下存在文章时不能被删除
    @Test
    void deleteCategory_categoryHaveArticle_throwCONFLICT() {
        Category category = new Category();
        category.setId(3);
        category.setCreatorId(7);

        when(categoryRepository.selectById(3)).thenReturn(category);
        when(articleRepository.selectCount(any())).thenReturn(1L);

        BusinessException exception = assertThrows(BusinessException.class, () -> categoryService.deleteCategory(3, 7));

        assertEquals(ErrorCode.CONFLICT, exception.getErrorCode());
        verify(categoryRepository, never()).deleteById(3);
    }
}
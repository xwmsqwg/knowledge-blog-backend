package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.CategoryCreateRequest;
import com.xrf.iotbackend.model.vo.CategoryVO;
import com.xrf.iotbackend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查询所有分类
    @GetMapping
    public Result getAllCategories(){
        List<CategoryVO> categoryVOList = categoryService.getAllCategories();
        return Result.success(categoryVOList);
    }

    //创建分类
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result createCategory(
            @Valid @RequestBody CategoryCreateRequest request,
            @RequestAttribute("currentUserId") Integer creatorId
    ) {
        CategoryVO categoryVO = categoryService.createCategory(request.getName(), creatorId);
        return Result.success("分类创建成功", categoryVO);
    }

    //删除分类
    @DeleteMapping("/{id}")
    public Result deleteCategory(
            @PathVariable("id") Integer categoryId,
            @RequestAttribute("currentUserId") Integer creatorId
    ) {
        categoryService.deleteCategory(categoryId , creatorId);
        return Result.successMessage("分类删除成功");
    }
}

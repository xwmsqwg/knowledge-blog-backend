package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.CategoryCreateRequest;
import com.xrf.iotbackend.model.vo.CategoryVO;
import com.xrf.iotbackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "分类", description = "分类查询、创建与删除")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "查询全部分类")
    @GetMapping
    public Result<List<CategoryVO>> getAllCategories(){
        List<CategoryVO> categoryVOList = categoryService.getAllCategories();
        return Result.success(categoryVOList);
    }

    @Operation(summary = "创建分类")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<CategoryVO> createCategory(
            @Valid @RequestBody CategoryCreateRequest request,
            @RequestAttribute("currentUserId") Integer creatorId
    ) {
        CategoryVO categoryVO = categoryService.createCategory(request.getName(), creatorId);
        return Result.success("分类创建成功", categoryVO);
    }

    @Operation(summary = "删除分类", description = "仅分类创建者可删除；分类下存在文章时不可删除")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(
            @PathVariable("id") Integer categoryId,
            @RequestAttribute("currentUserId") Integer creatorId
    ) {
        categoryService.deleteCategory(categoryId , creatorId);
        return Result.successMessage("分类删除成功");
    }
}

package com.xrf.iotbackend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {

    @NotBlank(message = "分类名称不能为空")
    @Size(min = 1 , max = 50, message = "分类名称不能为空，且长度不能超过50个字符")
    private String name;

}

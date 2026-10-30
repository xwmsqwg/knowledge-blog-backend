package com.xrf.iotbackend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//用于ArticleVO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorVO {

    private Integer id;
    private String username;

}

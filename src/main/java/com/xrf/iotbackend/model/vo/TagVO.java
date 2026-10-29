package com.xrf.iotbackend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {

    private Integer id;
    private String name;
    private Long articleCount;

}

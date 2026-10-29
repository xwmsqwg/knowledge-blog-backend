package com.xrf.iotbackend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrf.iotbackend.model.entity.Tag;
import com.xrf.iotbackend.model.vo.TagVO;

import java.util.List;

public interface TagRepository extends BaseMapper<Tag> {

    List<TagVO> selectAllTags();

}

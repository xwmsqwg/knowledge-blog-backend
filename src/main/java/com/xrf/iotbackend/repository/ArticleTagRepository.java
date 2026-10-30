package com.xrf.iotbackend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrf.iotbackend.model.entity.ArticleTag;
import com.xrf.iotbackend.model.vo.TagSimpleVO;

import java.util.List;

public interface ArticleTagRepository extends BaseMapper<ArticleTag> {

    List<TagSimpleVO> selectTagsByArticleId(Integer articleId);

}

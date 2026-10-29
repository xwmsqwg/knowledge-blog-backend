package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.model.vo.TagVO;
import com.xrf.iotbackend.repository.TagRepository;
import com.xrf.iotbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<TagVO> getAllTags() {
        return tagRepository.selectAllTags();
    }
}

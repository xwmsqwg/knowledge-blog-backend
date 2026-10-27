package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.repository.UserRepository;
import com.xrf.iotbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserVO getCurrUser(Integer userId) {
        User user = userRepository.selectById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.UNAUTHORIZED , "当前用户不存在或登录已失效");
        }
        return new UserVO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreateTime()
        );
    }
}

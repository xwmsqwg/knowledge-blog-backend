package com.xrf.iotbackend.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.RegisterRequest;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.repository.UserRepository;
import com.xrf.iotbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserVO register(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername().trim();
        String email = registerRequest.getEmail().trim();
        if (username.length() < 3 || username.length() > 32) {
            throw new BusinessException(
                    ErrorCode.VALIDATION_ERROR,
                    "用户名长度必须在3到32个字符之间"
            );
        }
        if (registerRequest.getPassword().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new BusinessException(
                    ErrorCode.VALIDATION_ERROR,
                    "密码不能超过72字节"
            );
        }
        long count = userRepository.selectCount(Wrappers.<User>lambdaQuery().eq(User::getUsername , username));
        if(count > 0){
            throw new BusinessException(ErrorCode.CONFLICT , "用户名已存在");
        }
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(email);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        try{
            userRepository.insert(user);
        } catch (DuplicateKeyException exception){
            throw new BusinessException(
                    ErrorCode.CONFLICT,
                    "用户名已存在"
            );
        }
        return new UserVO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreateTime()
        );
    }
}

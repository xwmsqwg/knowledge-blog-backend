package com.xrf.iotbackend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.LoginRequest;
import com.xrf.iotbackend.model.dto.RegisterRequest;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.LoginVO;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.repository.UserRepository;
import com.xrf.iotbackend.service.AuthService;
import com.xrf.iotbackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

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

    @Override
    public LoginVO login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername().trim();
        User existUser = userRepository.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getUsername , username));
        if(existUser == null){
            throw new BusinessException(ErrorCode.UNAUTHORIZED , "用户名或密码错误");
        }
        boolean isMatch = passwordEncoder.matches(
                loginRequest.getPassword(),
                existUser.getPasswordHash()
        );
        if(!isMatch){
            throw new BusinessException(ErrorCode.UNAUTHORIZED , "用户名或密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", existUser.getId());
        claims.put("username", existUser.getUsername());
        String token = jwtUtils.generateJwt(claims);
        UserVO userVO = new UserVO(
                existUser.getId(),
                existUser.getUsername(),
                existUser.getEmail(),
                existUser.getCreateTime()
        );
        return new LoginVO(token , userVO);
    }

}

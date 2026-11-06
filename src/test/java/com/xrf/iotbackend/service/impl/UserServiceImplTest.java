package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.LoginRequest;
import com.xrf.iotbackend.model.dto.RegisterRequest;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    @InjectMocks
    private AuthServiceImpl authService;

    // 测试用户存在，成功返回 UserVO
    @Test
    void getCurrUser_userExists_returnsUserVO() {
        LocalDateTime createTime = LocalDateTime.of(2026, 11, 6, 12, 0);
        User user = new User();
        user.setId(7);
        user.setUsername("tester");
        user.setEmail("test@163.com");
        user.setCreateTime(createTime);

        when(userRepository.selectById(7)).thenReturn(user);

        UserVO result = userService.getCurrUser(7);

        assertEquals(7, result.getId());
        assertEquals("tester", result.getUsername());
        assertEquals("test@163.com", result.getEmail());
        assertEquals(createTime, result.getCreateTime());
        verify(userRepository).selectById(7);
    }

    // 测试用户不存在，抛出 UNAUTHORIZED
    @Test
    void getCurrUser_userNotFound_throwsUnauthorized() {
        when(userRepository.selectById(7)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> userService.getCurrUser(7));

        assertEquals(ErrorCode.UNAUTHORIZED, exception.getErrorCode());
    }

    // 测试注册成功
    @Test
    void register_success(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(" test@163.com ");
        registerRequest.setPassword("123456 ");
        registerRequest.setUsername(" tester");

        when(userRepository.selectCount(any())).thenReturn(0L);
        when(passwordEncoder.encode("123456 ")).thenReturn("hash-123456");
        when(userRepository.insert(any(User.class))).thenReturn(1);

        UserVO result = authService.register(registerRequest);

        assertEquals("tester" , result.getUsername());
        assertEquals("test@163.com" , result.getEmail());
        assertNotNull(result.getCreateTime());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).insert(captor.capture());

        assertEquals("hash-123456", captor.getValue().getPasswordHash());
    }

    // 注册时密码过长
    @Test
    void register_passwordTooLong_throwsVALIDATION_ERROR(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(" test@163.com ");
        registerRequest.setPassword("a".repeat(73));
        registerRequest.setUsername(" tester");

        BusinessException exception = assertThrows(BusinessException.class , () -> authService.register(registerRequest));

        assertEquals(ErrorCode.VALIDATION_ERROR , exception.getErrorCode());

        verify(userRepository , never()).insert(any(User.class));
    }

    // 登录用户不存在
    @Test
    void login_userNotFound_throwsUnauthorized() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("tester");
        loginRequest.setPassword("123456");

        when(userRepository.selectOne(any())).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> authService.login(loginRequest));

        assertEquals(ErrorCode.UNAUTHORIZED, exception.getErrorCode());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    // 登录时密码错误
    @Test
    void login_passwordIsNotTrue_throwsUnauthorized(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("tester");
        loginRequest.setPassword("false_password");

        User user = new User();
        user.setPasswordHash("true_password_hash");

        when(userRepository.selectOne(any())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword() , user.getPasswordHash())).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class , () -> authService.login(loginRequest));

        assertEquals(ErrorCode.UNAUTHORIZED , exception.getErrorCode());
        verify(passwordEncoder).matches(loginRequest.getPassword() , user.getPasswordHash());
    }
}

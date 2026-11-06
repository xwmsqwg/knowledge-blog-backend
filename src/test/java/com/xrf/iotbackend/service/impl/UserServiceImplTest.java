package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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

    @Test
    void getCurrUser_userNotFound_throwsUnauthorized() {
        when(userRepository.selectById(7)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> userService.getCurrUser(7));

        assertEquals(ErrorCode.UNAUTHORIZED, exception.getErrorCode());
    }
}

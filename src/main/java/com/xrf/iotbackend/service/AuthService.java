package com.xrf.iotbackend.service;

import com.xrf.iotbackend.model.dto.LoginRequest;
import com.xrf.iotbackend.model.dto.RegisterRequest;
import com.xrf.iotbackend.model.vo.LoginVO;
import com.xrf.iotbackend.model.vo.UserVO;

public interface AuthService {

    UserVO register(RegisterRequest registerRequest);

    LoginVO login(LoginRequest loginRequest);

}

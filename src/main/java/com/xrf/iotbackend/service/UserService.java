package com.xrf.iotbackend.service;

import com.xrf.iotbackend.model.vo.UserVO;

public interface UserService {

    UserVO getCurrUser(Integer userId);

}

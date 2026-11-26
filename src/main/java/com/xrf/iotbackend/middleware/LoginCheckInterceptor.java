package com.xrf.iotbackend.middleware;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.regionMatches(true, 0, "Bearer ", 0, 7)){
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录");
        }

        String token = authorization.substring(7).trim();

        if(token.isEmpty()){
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录");
        }

        Claims claims = jwtUtils.parseJWT(token);

        if(claims == null){
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "token 无效或已过期");
        }

        Object idClaim = claims.get("id");

        if (!(idClaim instanceof Number number)){
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "Token 缺少用户信息");
        }

        request.setAttribute("currentUserId", number.intValue());
        return true;
    }

}

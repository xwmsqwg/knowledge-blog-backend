package com.xrf.iotbackend.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.regionMatches(true, 0, "Bearer ", 0, 7)){
            return unauthorized(response , "未登录");
        }

        String token = authorization.substring(7).trim();
        if(token.isEmpty()){
            return unauthorized(response , "未登录");
        }
        Claims claims = jwtUtils.parseJWT(token);
        if(claims == null){
            return unauthorized(response , "token 无效或已过期");
        }
        Object idClaim = claims.get("id");
        if (!(idClaim instanceof Number number)){
            return unauthorized(response, "Token 缺少用户信息");
        }
        request.setAttribute("currentUserId", number.intValue());
        return true;
    }

    private boolean unauthorized(HttpServletResponse response , String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        objectMapper.writeValue(
                response.getWriter(),
                Result.error(ErrorCode.UNAUTHORIZED.getCode(), message)
        );

        return false;
    }

}

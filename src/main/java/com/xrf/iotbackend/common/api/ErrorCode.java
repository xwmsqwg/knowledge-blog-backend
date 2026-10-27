package com.xrf.iotbackend.common.api;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SUCCESS(0, "success" , HttpStatus.OK),
    VALIDATION_ERROR(40001, "参数校验失败" , HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(40100, "未登录或 Token 无效" , HttpStatus.UNAUTHORIZED),
    FORBIDDEN(40300, "无权限操作" , HttpStatus.FORBIDDEN),
    NOT_FOUND(40400, "资源不存在" , HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(40500, "请求方法不支持", HttpStatus.METHOD_NOT_ALLOWED),
    CONFLICT(40900, "资源冲突" , HttpStatus.CONFLICT),
    INTERNAL_ERROR(50000, "服务器内部错误" , HttpStatus.INTERNAL_SERVER_ERROR);

    private final Integer code;
    private final String message;
    private final HttpStatus status;
}

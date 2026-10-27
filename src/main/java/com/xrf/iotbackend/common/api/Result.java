package com.xrf.iotbackend.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;
    private Long timestamp;

    public static Result success() {
        return new Result(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                null,
                System.currentTimeMillis()
        );
    }

    public static Result success(Object data) {
        return new Result(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                data,
                System.currentTimeMillis()
        );
    }

    public static Result successMessage(String message) {
        return new Result(
                ErrorCode.SUCCESS.getCode(),
                message,
                null,
                System.currentTimeMillis()
        );
    }

    public static Result success(String message, Object data) {
        return new Result(
                ErrorCode.SUCCESS.getCode(),
                message,
                data,
                System.currentTimeMillis()
        );
    }

    public static Result error(int code, String message) {
        return new Result(code, message, null, System.currentTimeMillis());
    }

}

package com.xrf.iotbackend.model;

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

    // 成功，无数据
    public static Result success() {
        return new Result(0, "success", null, System.currentTimeMillis());
    }

    // 成功，带数据
    public static Result success(Object data) {
        return new Result(0, "success", data, System.currentTimeMillis());
    }

    // 成功，仅自定义提示
    public static Result successMessage(String message) {
        return new Result(0, message, null, System.currentTimeMillis());
    }

    // 成功，自定义提示和数据
    public static Result success(String message, Object data) {
        return new Result(0, message, data, System.currentTimeMillis());
    }

    // 失败
    public static Result error(int code, String message) {
        return new Result(code, message, null, System.currentTimeMillis());
    }

}
package com.xrf.iotbackend.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "统一响应")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    @Schema(description = "业务状态码，0 表示成功", example = "0")
    private Integer code;
    @Schema(description = "提示信息", example = "success")
    private String message;
    @Schema(description = "业务数据；无返回数据时为 null")
    private T data;
    @Schema(description = "响应时间，毫秒时间戳", example = "1762142400000")
    private Long timestamp;

    public static Result<Void> success() {
        return new Result<>(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                null,
                System.currentTimeMillis()
        );
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getMessage(),
                data,
                System.currentTimeMillis()
        );
    }

    public static Result<Void> successMessage(String message) {
        return new Result<>(
                ErrorCode.SUCCESS.getCode(),
                message,
                null,
                System.currentTimeMillis()
        );
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(
                ErrorCode.SUCCESS.getCode(),
                message,
                data,
                System.currentTimeMillis()
        );
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null, System.currentTimeMillis());
    }

}

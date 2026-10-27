package com.xrf.iotbackend.exception;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.common.api.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Result> response(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(Result.error(errorCode.getCode(), message));
    }

    //处理业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return response(errorCode, exception.getMessage());
    }

    //处理@Valid校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? ErrorCode.VALIDATION_ERROR.getMessage() : fieldError.getDefaultMessage();
        return response(ErrorCode.VALIDATION_ERROR, message);
    }

    //处理方法参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result> handleConstraintViolation(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation -> violation.getMessage())
                .orElse(ErrorCode.VALIDATION_ERROR.getMessage());
        return response(ErrorCode.VALIDATION_ERROR, message);
    }

    //处理请求体错误异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result> handleUnreadableMessage() {
        return response(ErrorCode.VALIDATION_ERROR, "请求 JSON 格式错误");
    }

    //请求方法错误异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result> handleMethodNotSupported(){
        return response(ErrorCode.METHOD_NOT_ALLOWED , "请求方式错误");
    }

    //兜底异常处理
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleUnknownException(Exception exception) {
        log.error("未处理的服务器异常", exception);
        return response(
                ErrorCode.INTERNAL_ERROR,
                ErrorCode.INTERNAL_ERROR.getMessage()
        );
    }

}
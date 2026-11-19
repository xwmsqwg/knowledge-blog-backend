package com.xrf.iotbackend.exception;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Result<?>> response(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(Result.error(errorCode.getCode(), message));
    }

    //处理业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.warn("业务异常：code = {} , message = {}" , errorCode.getCode() , exception.getMessage());
        return response(errorCode, exception.getMessage());
    }

    //处理@Valid校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? ErrorCode.VALIDATION_ERROR.getMessage() : fieldError.getDefaultMessage();
        if(fieldError == null){
            log.warn("@Valid 校验失败: message={}", message);
        }
        else {
            log.warn("@Valid 校验失败: field={}, message={}", fieldError.getField(), message);
        }
        return response(ErrorCode.VALIDATION_ERROR, message);
    }

    //处理请求体错误异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<?>> handleUnreadableMessage() {
        log.warn("请求体解析失败");
        return response(ErrorCode.VALIDATION_ERROR, "请求 JSON 格式错误");
    }

    //请求方法错误异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException exception){
        log.warn("请求方式错误: method={}, supported={}", exception.getMethod(), exception.getSupportedHttpMethods());
        return response(ErrorCode.METHOD_NOT_ALLOWED , "请求方式错误");
    }

    //处理请求参数异常
    @ExceptionHandler({
            HandlerMethodValidationException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<Result<?>> handleRequestParameterError(Exception exception) {
        log.warn("请求参数错误: type={}" , exception.getClass().getSimpleName());
        return response(ErrorCode.VALIDATION_ERROR, "请求参数错误");
    }

    // 处理请求路径异常
    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<Result<?>> handlerNotFound(Exception exception){
        log.warn("资源不存在");
        return response(ErrorCode.NOT_FOUND , "请求资源不存在");
    }

    //兜底异常处理
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleUnknownException(Exception exception) {
        log.error("未处理的服务器异常", exception);
        return response(
                ErrorCode.INTERNAL_ERROR,
                ErrorCode.INTERNAL_ERROR.getMessage()
        );
    }

}
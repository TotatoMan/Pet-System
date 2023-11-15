package com.bcb.exception;

import com.bcb.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex)
    {
        ex.printStackTrace();
        return Result.error("对不起，操作失败");
    }
}

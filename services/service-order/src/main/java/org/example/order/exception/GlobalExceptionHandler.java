package org.example.order.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 合成了2个异常处理类@ResponseBody + @ControllerAdvice

@RestControllerAdvice   // 全局异常处理器
public class GlobalExceptionHandler {

    // 每一个全局异常处理器都稍微会有不一样
//    @ExceptionHandler(Throwable.class)
//    public String error(Throwable e){
//        return "";
//    }

}

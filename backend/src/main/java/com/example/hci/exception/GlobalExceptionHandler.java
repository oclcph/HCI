package com.example.hci.exception;

import com.example.hci.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//通过注解标记这个类是一个全局异常处理器。
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HCIException.class)
    public ResultVO<String> handleAIExternalException(HCIException e) {
        e.printStackTrace();//打印异常堆栈信息。
        return ResultVO.buildFailure(e.getMessage());
    }
}

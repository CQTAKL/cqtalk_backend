package com.cqtalk.util.returnObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    /**
     * 全局异常捕捉处理
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public static ObjectResult exceptionGet(Exception e) {
        // 异常处理逻辑
        if (e instanceof DescribeException) {
            DescribeException MyException = (DescribeException) e;
            return ObjectResult.error(MyException.getCode(), MyException.getMessage());
        }
        // 如果错误未知，则打印到日志中
        log.error("系统异常:", e);
        return ObjectResult.error(ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getMsg());
    }
}
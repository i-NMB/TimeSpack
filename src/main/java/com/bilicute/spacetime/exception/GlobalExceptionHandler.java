package com.bilicute.spacetime.exception;

import com.bilicute.spacetime.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ytt
 * @description 全局报错输出
 * @date 2024/4/16
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @param e:  [e] 错误详情
     * @return Result
     * @author ytt
     * @description 全局报错输出以api形式反馈
     * @date 2024/4/16
     */
    @ExceptionHandler (Exception.class )
    public Result<String> handleException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage():"操作失败");
    }
}

package com.bilicute.spacetime.exception;

import com.bilicute.spacetime.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * @param e:  [e] 错误详情
     * @return Result
     * @author ytt
     * @description 全局报错输出以api形式反馈
     * @date 2024/4/16
     */
    @ExceptionHandler (Exception.class )
    public Result<String> handleException(Exception e){
        logger.error("出现错误:", e);
//        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage():"操作失败");
    }
}

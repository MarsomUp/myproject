package com.weina.web;

import com.weina.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 统一异常处理
 * @Author: mayc
 * @Date: 2019/02/13 17:05
 */
@ControllerAdvice
public class HandlerException {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerException.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        LOGGER.error("发生未捕获的异常", e);
        return new Result(500, "Oppps! there happend some error");
    }

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Result myExceptionHandler(BusinessException e) {
        LOGGER.error("发生未捕获异常", e);
        Result result = new Result();
        result.setCode(e.getCode() == 0 ? 500 : e.getCode());
        result.setMsg(StringUtils.isBlank(e.getMsg()) ? "系统错误" : e.getMsg());
        return result;
    }
}

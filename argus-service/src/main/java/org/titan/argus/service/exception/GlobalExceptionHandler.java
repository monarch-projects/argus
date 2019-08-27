package org.titan.argus.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.titan.argus.model.response.ObjectDataResponse;

/**
 * @Title: GlobalExceptionHandler
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler({BusinessException.class, Exception.class})
    public ObjectDataResponse<Void> handler(Exception ex) {
        if (ex instanceof BusinessException) {
            log.error("business error", ex);
            BusinessException be = (BusinessException) ex;

            return new ObjectDataResponse<>(be.getCode(), be.getMessage());
        }
        log.error("internal error", ex);
        return new ObjectDataResponse<>(500, "服务器内部错误");
    }
}

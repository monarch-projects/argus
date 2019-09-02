package org.titan.argus.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.exception.BusinessException;

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
        return new ObjectDataResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");
    }
}

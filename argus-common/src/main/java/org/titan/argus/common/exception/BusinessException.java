package org.titan.argus.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Title: BusinessException
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private int code;
    private String message;

    public BusinessException(String message) {
        this.code = 2000;
        this.message = message;
    }
}

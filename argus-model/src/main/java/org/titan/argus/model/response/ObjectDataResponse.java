package org.titan.argus.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Title: ObjectDataResponse
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectDataResponse<T> {

    private int code = 200;
    private String message = "OK";
    private T data;

    public ObjectDataResponse(T data) {
        this.data = data;
    }

    public ObjectDataResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

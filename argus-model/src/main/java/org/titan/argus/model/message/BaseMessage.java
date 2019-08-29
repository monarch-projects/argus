package org.titan.argus.model.message;

import lombok.Data;

import java.util.Map;

/**
 * @Title: BaseMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
public class BaseMessage {
    public static final int RATE_LIMIT = 1;

    private Integer type;
    private Map<String, String> extra;


}

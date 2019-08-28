package org.titan.argus.plugin.ratelimit.commons.domain;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Title: RateLimitMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@Data
public class RateLimitMessage {
    private String className;
    private String methodName;
    private int limit;
}

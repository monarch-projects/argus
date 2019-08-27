package org.titan.argus.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Title: RateLimitClientDataRequest
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@Data
@Accessors(chain = true)
public class RateLimitClientDataRequest {

    private String appName;
    private String clzName;
    private String methodName;
    private Integer limit;
    private Integer waitTime;
}

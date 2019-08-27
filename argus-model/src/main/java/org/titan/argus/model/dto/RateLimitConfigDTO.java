package org.titan.argus.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Title: RateLimitConfigDTO
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
@Accessors(chain = true)
public class RateLimitConfigDTO {
    private String clzName;
    private String methodName;
    private Integer limit;
    private Integer waitTime;
}

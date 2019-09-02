package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Title: RateLimitInfo
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@TableName("rate_limit_info")
@Data
@Accessors(chain = true)
public class RateLimitInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String appName;
    private String clzName;
    private String methodName;
    @TableField("`limit`")
    private Integer limit;
    private Integer waitTime;

    private Long created;
    private Long updated;
    @TableLogic
    private Boolean deleted;
}

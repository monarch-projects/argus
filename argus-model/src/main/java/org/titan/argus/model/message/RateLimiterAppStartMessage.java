package org.titan.argus.model.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.titan.argus.model.request.RateLimitClientDataRequest;

import java.util.Collection;

/**
 * @Title: RateLimiterAppStartMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RateLimiterAppStartMessage extends BaseMessage {

    private Collection<RateLimitClientDataRequest> data;

    private String appName;


    public RateLimiterAppStartMessage() {
        this.setType(BaseMessage.RATE_LIMIT_ONLINE);
    }
}

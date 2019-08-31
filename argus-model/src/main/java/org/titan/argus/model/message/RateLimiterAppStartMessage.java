package org.titan.argus.model.message;

import lombok.Data;
import lombok.experimental.Accessors;
import org.titan.argus.model.request.RateLimitClientDataRequest;
import org.titan.argus.util.JsonUtil;

import java.util.Collection;

/**
 * @Title: RateLimiterAppStartMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Data
@Accessors(chain = true)
public class RateLimiterAppStartMessage implements ArgusMessage {

    private Collection<RateLimitClientDataRequest> data;

    private String appName;

    @Override
    public String json() {
        return JsonUtil.encode(this);
    }
}

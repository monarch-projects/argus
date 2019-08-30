package org.titan.argus.model.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @Title: BaseMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
@Accessors(chain = true)
public class BaseMessage implements Serializable {

    public static final String RATE_LIMIT_MESSAGE_KEY = "argus-rate-limit";

    public static final int RATE_LIMIT_UPDATE = 1;
    public static final int RATE_LIMIT_ONLINE = 2;
    public static final int RATE_LIMIT_CONFIGS = 3;

    private Integer type;
    private Map<String, String> extra;


    @SuppressWarnings("unchecked")
    public <T extends BaseMessage> T convert() {
        switch (this.type) {
            case 1:
                UpdateRateLimitMessage message = (UpdateRateLimitMessage) this;
                return (T) message;
            case 2:
                RateLimiterAppStartMessage startMessage = (RateLimiterAppStartMessage) this;
                return (T) startMessage;
            case 3:
                UpdateRateLimitMessage limitMessage = (UpdateRateLimitMessage) this;
                return (T) limitMessage;
            default:
                return null;
        }
    }
}

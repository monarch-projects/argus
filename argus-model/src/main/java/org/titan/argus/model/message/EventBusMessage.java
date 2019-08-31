package org.titan.argus.model.message;

import lombok.Data;
import lombok.experimental.Accessors;
import org.titan.argus.util.JsonUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @Title: EventBusMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
@Accessors(chain = true)
public class EventBusMessage implements Serializable {

    public static final int RATE_LIMIT_UPDATE = 1;
    public static final int RATE_LIMIT_ONLINE = 2;
    public static final int RATE_LIMIT_CONFIGS = 3;

    private Integer type;
    private Map<String, String> extra;

    private String data;


    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> clz) {
        return JsonUtil.decode(this.data, clz);
    }


    public static EventBusMessage getInstance(int type) {
        return new EventBusMessage().setType(type);
    }
}

package org.titan.argus.model.message;

import lombok.Data;
import lombok.experimental.Accessors;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.util.JsonUtil;

import java.util.Collection;

/**
 * @Title: UpdateRateLimitMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
@Accessors(chain = true)
public class UpdateRateLimitMessage implements ArgusMessage {

    private Collection<RateLimitConfigDTO> configs;


    @Override
    public String json() {
        return JsonUtil.encode(this);
    }
}

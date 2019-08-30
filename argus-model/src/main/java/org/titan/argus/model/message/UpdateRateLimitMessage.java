package org.titan.argus.model.message;

import lombok.Data;
import lombok.experimental.Accessors;
import org.titan.argus.model.dto.RateLimitConfigDTO;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Title: UpdateRateLimitMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
@Accessors(chain = true)
public class UpdateRateLimitMessage extends BaseMessage implements Serializable {

    private Collection<RateLimitConfigDTO> configs;


    public UpdateRateLimitMessage() {
        this.setType(BaseMessage.RATE_LIMIT_UPDATE);
    }
}

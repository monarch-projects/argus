package org.titan.argus.model.message;

import org.titan.argus.model.dto.RateLimitConfigDTO;

import java.util.Collection;

/**
 * @Title: RateLimitServerUpdateMessage
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
public class RateLimitServerUpdateMessage extends BaseMessage {

    private Collection<RateLimitConfigDTO> configs;
}

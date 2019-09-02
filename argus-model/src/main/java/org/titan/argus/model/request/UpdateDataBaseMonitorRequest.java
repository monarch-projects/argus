package org.titan.argus.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: UpdateDataBaseMonitorRequest
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateDataBaseMonitorRequest extends AddDataBaseMonitorRequest {
    private Long id;
}

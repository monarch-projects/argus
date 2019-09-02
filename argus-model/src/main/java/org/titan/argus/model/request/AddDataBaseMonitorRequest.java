package org.titan.argus.model.request;

import lombok.Data;

/**
 * @Title: AddDataBaseMonitorRequest
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Data
public class AddDataBaseMonitorRequest {
    private String host;
    private Integer port;
    private String dbName;
    private String username;
    private String password;
}

package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Title: DataBaseMonitor
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Data
@Accessors(chain = true)
@TableName("data_base_monitor")
public class DataBaseMonitor {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String host;
    private Integer port;
    private String dbName;
    private String username;
    private String password;
    private Long created;
    private Long updated;
    //0 停止  1 运行
    @TableField("`status`")
    private Short status;
    //1 mysql
    private Short type;
    @TableLogic
    private Boolean deleted;
}

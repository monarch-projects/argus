package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableLogic
    private Boolean deleted;
}

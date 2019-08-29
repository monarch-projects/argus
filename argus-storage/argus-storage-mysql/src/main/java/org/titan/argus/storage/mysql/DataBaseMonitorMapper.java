package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.titan.argus.model.entities.DataBaseMonitor;

/**
 * @Title: DataBaseMonitorMapper
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Repository
public interface DataBaseMonitorMapper extends BaseMapper<DataBaseMonitor> {
}

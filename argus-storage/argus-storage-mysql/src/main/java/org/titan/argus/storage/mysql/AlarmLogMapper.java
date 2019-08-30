package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.titan.argus.model.entities.AlarmLog;

/**
 * @author starboyate
 */
@Mapper
public interface AlarmLogMapper extends BaseMapper<AlarmLog> {
}

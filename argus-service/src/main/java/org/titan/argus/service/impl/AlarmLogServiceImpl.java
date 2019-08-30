package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.titan.argus.model.entities.AlarmLog;
import org.titan.argus.service.AlarmLogService;
import org.titan.argus.storage.mysql.AlarmLogMapper;

/**
 * @author starboyate
 */
@Service
public class AlarmLogServiceImpl extends ServiceImpl<AlarmLogMapper, AlarmLog> implements AlarmLogService {
}

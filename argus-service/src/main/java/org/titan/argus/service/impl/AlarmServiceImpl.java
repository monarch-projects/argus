package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.service.AlarmService;
import org.titan.argus.storage.mysql.AlarmMapper;

/**
 * @author starboyate
 */
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements AlarmService {
}

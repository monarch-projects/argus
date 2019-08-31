package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.titan.argus.model.entities.DataBaseMonitor;
import org.titan.argus.model.request.AddDataBaseMonitorRequest;
import org.titan.argus.model.request.UpdateDataBaseMonitorRequest;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.service.util.DateUtil;
import org.titan.argus.storage.mysql.DataBaseMonitorMapper;

import java.util.Objects;

/**
 * @Title: DataBaseMonitorServiceImpl
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Service
@Slf4j
@Primary
public class DataBaseMonitorServiceImpl extends ServiceImpl<DataBaseMonitorMapper, DataBaseMonitor> {

    public boolean add(AddDataBaseMonitorRequest request) {
        long now = DateUtil.currentTime();
        DataBaseMonitor monitor = new DataBaseMonitor();
        BeanUtils.copyProperties(request, monitor);
        monitor.setCreated(now).setUpdated(now);

        return this.save(monitor);
    }


    public boolean update(UpdateDataBaseMonitorRequest request) {
        DataBaseMonitor monitor = this.getById(request.getId());
        if (Objects.isNull(monitor))
            throw new BusinessException("该内容不存在！");

        long now = DateUtil.currentTime();
        BeanUtils.copyProperties(request, monitor);
        monitor.setUpdated(now);

        return this.updateById(monitor);
    }

}

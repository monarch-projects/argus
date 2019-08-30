package org.titan.argus.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.titan.argus.storage.es.domain.DataBaseMonitorOriginData;
import org.titan.argus.storage.es.repo.DataBaseMonitorOriginDataRepository;

/**
 * @Title: DataBaseMonitorTaskAsync
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Component
public class DataBaseMonitorTaskAsync {

    @Autowired
    private DataBaseMonitorOriginDataRepository dataRepository;

    @Async
    public void doAsync(DataBaseMonitorOriginData data) {
        //TODO
        this.dataRepository.save(data);
    }
}

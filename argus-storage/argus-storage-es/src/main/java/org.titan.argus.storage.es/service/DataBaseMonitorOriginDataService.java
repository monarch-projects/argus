package org.titan.argus.storage.es.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.titan.argus.storage.es.repo.DataBaseMonitorOriginDataRepository;

/**
 * @Title: DataBaseMonitorOriginDataService
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Service
@Slf4j
@Primary
public class DataBaseMonitorOriginDataService {
    @Autowired
    private DataBaseMonitorOriginDataRepository originDataRepository;
}

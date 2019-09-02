package org.titan.argus.storage.es.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.titan.argus.storage.es.domain.DataBaseMonitorOriginData;

/**
 * @Title: DataBaseMonitorOriginDataRepository
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Repository
public interface DataBaseMonitorOriginDataRepository extends ElasticsearchRepository<DataBaseMonitorOriginData, Long> {
}

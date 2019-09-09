package org.titan.argus.storage.es.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.titan.argus.storage.es.domain.MongodbMonitorNodeInfo;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;

/**
 * @author starboyate
 */
@Repository
public interface MongodbMonitorNodeInfoRepository extends ElasticsearchRepository<MongodbMonitorNodeInfo, Long> {
}
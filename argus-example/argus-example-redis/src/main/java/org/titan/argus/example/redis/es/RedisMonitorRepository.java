package org.titan.argus.example.redis.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author starboyate
 */
@Repository
public interface RedisMonitorRepository extends ElasticsearchRepository<RedisNodeInfo, Long> {
}

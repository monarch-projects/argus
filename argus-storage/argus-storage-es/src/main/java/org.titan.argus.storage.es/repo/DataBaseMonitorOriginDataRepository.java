package org.titan.argus.storage.es.repo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.titan.argus.storage.es.domain.DataBaseMonitorOriginData;

/**
 * @Title: DataBaseMonitorOriginDataRepository
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = {"org.titan.argus.storage.es.repo"})
public interface DataBaseMonitorOriginDataRepository extends ElasticsearchRepository<DataBaseMonitorOriginData, Long> {
}

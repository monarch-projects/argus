package org.titan.argus.storage.es.service.impl;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.storage.es.domain.MongodbMonitorNodeInfo;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;
import org.titan.argus.storage.es.repo.MongodbMonitorNodeInfoRepository;
import org.titan.argus.storage.es.repo.RedisMonitorNodeInfoRepository;
import org.titan.argus.storage.es.service.BaseMonitorService;
import org.titan.argus.storage.es.service.MongodbMonitorNodeInfoService;
import org.titan.argus.storage.es.service.RedisMonitorNodeInfoService;

import java.util.List;


/**
 * @author starboyate
 */
@Service
public class MongodbMonitorNodeInfoServiceImpl extends BaseMonitorService implements MongodbMonitorNodeInfoService {
	@Autowired
	private MongodbMonitorNodeInfoRepository mongodbMonitorNodeInfoRepository;


	@Override
	public MongodbMonitorNodeInfo save(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
		return mongodbMonitorNodeInfoRepository.save(mongodbMonitorNodeInfo);
	}

	@Override
	public long count() {
		return mongodbMonitorNodeInfoRepository.count();
	}

	@Override
	public boolean delete(MongodbMonitorNodeInfo redisMonitor) {
		mongodbMonitorNodeInfoRepository.delete(redisMonitor);
		return true;
	}


	@Override
	public List<MongodbMonitorNodeInfo> findAll() {
		return Lists.newArrayList(mongodbMonitorNodeInfoRepository.findAll());
	}

	@Override
	public List<MongodbMonitorNodeInfo> findByIp(String ip) {
		return Lists.newArrayList(mongodbMonitorNodeInfoRepository.search(new MatchQueryBuilder("ip", ip)));
	}

	@Override
	public List<MongodbMonitorNodeInfo> findByTime(Long startTime, Long endTime) {
		return Lists.newArrayList(mongodbMonitorNodeInfoRepository.search(getRangerBuilder(startTime, endTime)));
	}

	@Override
	public List<MongodbMonitorNodeInfo> findByTimeAndIp(String ip, Long startTime, Long endTime) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime").gte(startTime).lte(endTime);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
				.must(getRangerBuilder(startTime, endTime))
				.must(QueryBuilders.matchQuery("ip", ip));
		return Lists.newArrayList(mongodbMonitorNodeInfoRepository.search(boolQueryBuilder));
	}

	@Override
	public List<MongodbMonitorNodeInfo> saveAll(List<MongodbMonitorNodeInfo> list) {
		return Lists.newArrayList(mongodbMonitorNodeInfoRepository.saveAll(list));
	}
}

package org.titan.argus.storage.es.service.impl;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;
import org.titan.argus.storage.es.repo.RedisMonitorNodeInfoRepository;
import org.titan.argus.storage.es.service.RedisMonitorNodeInfoService;

import java.util.List;


/**
 * @author starboyate
 */
@Service
public class RedisMonitorNodeInfoServiceImpl implements RedisMonitorNodeInfoService {
	@Autowired
	private RedisMonitorNodeInfoRepository redisMonitorRepository;


	@Override
	public RedisMonitorNodeInfo save(RedisMonitorNodeInfo redisMonitor) {
		return redisMonitorRepository.save(redisMonitor);
	}

	@Override
	public long count() {
		return redisMonitorRepository.count();
	}

	@Override
	public boolean delete(RedisMonitorNodeInfo redisMonitor) {
		redisMonitorRepository.delete(redisMonitor);
		return true;
	}


	@Override
	public List<RedisMonitorNodeInfo> findAll() {
		return Lists.newArrayList(redisMonitorRepository.findAll());
	}

	@Override
	public List<RedisMonitorNodeInfo> findByIp(String ip) {
		return Lists.newArrayList(redisMonitorRepository.search(new MatchQueryBuilder("ip", ip)));
	}

	@Override
	public List<RedisMonitorNodeInfo> findByTime(Long startTime, Long endTime) {
		return Lists.newArrayList(redisMonitorRepository.search(QueryBuilders.rangeQuery("createTime").gte(startTime).lte(endTime)));
	}

	@Override
	public List<RedisMonitorNodeInfo> findByTimeAndIp(String ip, Long startTime, Long endTime) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime").gte(startTime).lte(endTime);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.rangeQuery("createTime").gte(startTime).lte(endTime))
				.must(QueryBuilders.matchQuery("ip", ip));
		return Lists.newArrayList(redisMonitorRepository.search(boolQueryBuilder));
	}

	@Override
	public List<RedisMonitorNodeInfo> saveAll(List<RedisMonitorNodeInfo> list) {
		return Lists.newArrayList(redisMonitorRepository.saveAll(list));
	}
}

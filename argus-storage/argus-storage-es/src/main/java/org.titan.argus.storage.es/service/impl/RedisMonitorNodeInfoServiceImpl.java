package org.titan.argus.storage.es.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;
import org.titan.argus.storage.es.repo.RedisMonitorNodeInfoRepository;
import org.titan.argus.storage.es.service.BaseMonitorService;
import org.titan.argus.storage.es.service.RedisMonitorNodeInfoService;

import java.util.List;


/**
 * @author starboyate
 */
@Service
public class RedisMonitorNodeInfoServiceImpl extends BaseMonitorService implements RedisMonitorNodeInfoService {
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
	public List<RedisMonitorNodeInfo> findAll(Integer page, Integer size) {
		return Lists.newArrayList(redisMonitorRepository.search(QueryBuilders.boolQuery(), PageRequest.of(page, size)));
	}

	@Override
	public List<RedisMonitorNodeInfo> findByIp(String ip, Integer page, Integer size) {
		return Lists.newArrayList(redisMonitorRepository.search(new MatchQueryBuilder("ip", ip), PageRequest.of(page, size)));
	}

	@Override
	public List<RedisMonitorNodeInfo> findByTime(Long startTime, Long endTime, Integer page, Integer size) {
		return Lists.newArrayList(redisMonitorRepository.search(getRangerBuilder(startTime, endTime), PageRequest.of(page, size)));
	}


	@Override
	public List<RedisMonitorNodeInfo> findByTimeAndIp(String ip, Long startTime, Long endTime, Integer page, Integer size) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
				.must(getRangerBuilder(startTime, endTime))
				.must(QueryBuilders.matchQuery("ip", ip));
		return Lists.newArrayList(redisMonitorRepository.search(boolQueryBuilder, PageRequest.of(page, size)));
	}

	@Override
	public List<RedisMonitorNodeInfo> saveAll(List<RedisMonitorNodeInfo> list) {
		return Lists.newArrayList(redisMonitorRepository.saveAll(list));
	}
}

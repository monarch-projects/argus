package org.titan.argus.example.redis.es;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author starboyate
 */
@Service
public class RedisMonitorServiceImpl implements RedisMonitorService {
	@Autowired
	private RedisMonitorRepository redisMonitorRepository;

	@Override
	public RedisNodeInfo save(RedisNodeInfo redisMonitor) {
		return redisMonitorRepository.save(redisMonitor);
	}

	@Override
	public long count() {
		return redisMonitorRepository.count();
	}

	@Override
	public boolean delete(RedisNodeInfo redisMonitor) {
		redisMonitorRepository.delete(redisMonitor);
		return true;
	}

	@Override
	public List<RedisNodeInfo> findAll() {
		return Lists.newArrayList(redisMonitorRepository.findAll());
	}

	@Override
	public List<RedisNodeInfo> findByIp(String ip) {
		return Lists.newArrayList(redisMonitorRepository.search(new MatchQueryBuilder("ip", ip)));
	}
}

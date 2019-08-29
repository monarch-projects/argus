package org.titan.argus.tools.alarm.core;

import org.titan.argus.model.entities.Alarm;
import org.titan.argus.model.entities.RedisNode;
import org.titan.argus.model.entities.RedisNodeInfo;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class RedisAlarmHandler implements AlarmHandler {

	@Override
	public void handler(Alarm alarm) {
		Set<RedisNodeInfo> redisNodeInfoSet = MiddleWareNodeHolder.getRedisNodeInfoSet();
		for (RedisNodeInfo info : redisNodeInfoSet) {
			if (info.getIsCluster()) {
				redisClusterHandler(info.getNodeList());
			} else if (info.getIsSentinel()) {

			}
		}
	}

	private void redisClusterHandler(List<RedisNode> nodeList) {
		Set<HostAndPort> collect = nodeList.stream().map(item -> new HostAndPort(item.getHost(), item.getPort()))
				.collect(Collectors.toSet());
		JedisCluster cluster = new JedisCluster(collect);
		Map<String, JedisPool> clusterNodes = cluster.getClusterNodes();
	}

	private void redisSentinelHandler(List<RedisNode> nodeList) {
		Set<String> collect = nodeList.stream().map(item -> item.getHost() + ":" + item.getPort())
				.collect(Collectors.toSet());
		JedisSentinelPool pool = new JedisSentinelPool("myMaster", collect);
		Jedis resource = pool.getResource();
	}

}

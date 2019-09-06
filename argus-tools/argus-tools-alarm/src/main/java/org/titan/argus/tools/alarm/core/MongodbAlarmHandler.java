//package org.titan.argus.tools.alarm.core;
//
//import org.titan.argus.model.entities.Alarm;
//import redis.clients.jedis.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @author starboyate
// */
//public class MongodbAlarmHandler implements AlarmHandler {
//
//
//	@Override
//	public void handler(Alarm alarm) {
//
//	}
//
//	private void redisClusterHandler(List<RedisNode> nodeList) {
//		Set<HostAndPort> collect = nodeList.stream().map(item -> new HostAndPort(item.getHost(), item.getPort()))
//				.collect(Collectors.toSet());
//		JedisCluster cluster = new JedisCluster(collect);
//		Map<String, JedisPool> clusterNodes = cluster.getClusterNodes();
//	}
//
//	private void redisSentinelHandler(List<RedisNode> nodeList) {
//		Set<String> collect = nodeList.stream().map(item -> item.getHost() + ":" + item.getPort())
//				.collect(Collectors.toSet());
//		JedisSentinelPool pool = new JedisSentinelPool("myMaster", collect);
//		Jedis resource = pool.getResource();
//	}
//
//}

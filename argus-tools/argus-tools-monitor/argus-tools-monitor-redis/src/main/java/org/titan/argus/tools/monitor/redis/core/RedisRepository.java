package org.titan.argus.tools.monitor.redis.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;
import org.titan.argus.tools.monitor.redis.domain.*;
import org.titan.argus.util.SnowFakeIdUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class RedisRepository {
	private static final Logger logger = LoggerFactory.getLogger(RedisRepository.class);

	private static final Cache<String, Jedis> REDIS_CLIENT_CACHE;

	private static final Integer MAX_SIZE = 50;

	private static final Integer EXPIRE_TIME = 10;

	static {
		REDIS_CLIENT_CACHE = CacheBuilder.newBuilder()
				.maximumSize(MAX_SIZE)
				.expireAfterAccess(EXPIRE_TIME, TimeUnit.MINUTES)
				.removalListener((RemovalNotification<String, Jedis> notification) -> {
					notification.getValue().close();
					logger.info("jedis close, key is " + notification.getKey());
				})
				.build();
	}

	public static Jedis create(String host, Integer port, String password) {
		Jedis jedis = REDIS_CLIENT_CACHE.getIfPresent(host + ":" + port);
		if (null == jedis) {
			jedis = new Jedis(host, port);
			if (StringUtils.isNotBlank(password)) {
				jedis.auth(password);
			}
		}
		return jedis;
	}

	public static Jedis create(String host, Integer port) {
		return create(host, port, null);
	}

	public static String getRedisModeCode(Jedis jedis) {
		Map<String, String> server = getInfo(jedis, "server");
		return server.get("redis_mode");
	}


	private static Map<String, String> getInfo(Jedis jedis, String selection) {
		String result;
		if (StringUtils.isBlank(selection)) {
			result = jedis.info();
		} else {
			result = jedis.info(selection);
		}
		String[] split = result.split("\r\n");
		Map<String, String> map = Arrays.stream(result.split("\r\n"))
				.filter(item -> !item.contains("#") && StringUtils.isNotBlank(item))
				.collect(Collectors.toMap(k -> k.substring(0, k.lastIndexOf(":")), v -> v.substring(v.lastIndexOf(":") + 1)));
		return map;
	}

	private static Map<String, String> getInfo(Jedis jedis) {
		return getInfo(jedis, null);
	}

	public static Object getRedisConfig(RedisNode node) {
		Jedis jedis = create(node.getHost(), node.getPort(), node.getPassword());
		return jedis.configGet("*");
	}

	public static Map<String, String> setRedisConfig(RedisNode node, Map<String, String> parms) {
		Jedis jedis = create(node.getHost(), node.getPort(), node.getPassword());
		Map<String, String> tempMap = new HashMap<>();
		parms.forEach((k, v) -> {
			try {
				jedis.configSet(k, v);
				tempMap.put(k, v);
			} catch (Exception ex) {
				logger.error("set config error, error msg: ", ex);
			}
		});
		return tempMap;
	}

	public static RedisNodeSimpleInfo getSimpleNodeInfo(RedisNode node) {
		Jedis jedis = create(node.getHost(), node.getPort(), node.getPassword());
		String modeName = getRedisModeCode(jedis);
		List<RedisNodeMetadataInfo> nodeList = getNodeList(node);
		List<RedisNode> nodes = nodeList.stream()
				.map(item -> RedisNode.builder().host(item.getHost()).port(item.getPort()).build())
				.collect(Collectors.toList());
		RedisNodeSimpleInfo simpleInfo = new RedisNodeSimpleInfo();
		Map<String, String> map = getInfo(jedis);
		simpleInfo.setNodes(nodes).setVersion(map.get("redis_version")).setMode(modeName).setNodeSize(nodes.size());
		return simpleInfo;
	}


	public static List<RedisNodeMetadataInfo> getNodeList(RedisNode node) {
		Jedis jedis = create(node.getHost(), node.getPort(), node.getPassword());
		String modeName = getRedisModeCode(jedis);
		List<RedisNodeMetadataInfo> list = new ArrayList<>();
		if (RedisModeEnum.CLUSTER.getName().equals(modeName)) {
			String[] strings = jedis.clusterNodes().split("\n");
			for (String s : strings) {
				String[] splits = s.split("\\s+");
				String hostAndPort = splits[1];
				String host = hostAndPort.substring(0, hostAndPort.lastIndexOf(":"));
				Integer port = Integer.valueOf(hostAndPort.substring(hostAndPort.lastIndexOf(":") + 1));
				RedisNodeMetadataInfo build = RedisNodeMetadataInfo.builder().host(host).port(port)
						.role(splits[2].contains("master") ? "master" : "slave").slot(splits[splits.length - 1])
						.build();
				list.add(build);
			}
		} else {
			Map<String, String> info = getInfo(jedis, "Replication");
			info.forEach((k, v) -> {
				if (k.startsWith("slave")) {
					String[] strings = v.split(",");
					String host = strings[0].substring(strings[0].lastIndexOf("=") + 1);
					Integer port = Integer.valueOf(strings[1].substring(strings[1].lastIndexOf("=") + 1));
					RedisNodeMetadataInfo slave = RedisNodeMetadataInfo.builder().host(host).port(port).role("slave")
							.build();
					list.add(slave);
				}
			});
			list.add(
					RedisNodeMetadataInfo.builder()
							.host(node.getHost())
							.port(node.getPort())
							.role(info.get("role"))
							.build()
			);
		}
		return list;
	}

	public static RedisMetricInfo getRedisMetricInfo(RedisNode redisNode) {
		Jedis jedis = create(redisNode.getHost(), redisNode.getPort(), redisNode.getPassword());
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		RedisMetricInfo redisInfo = getRedisInfo(jedis, RedisMetricInfo.class);
		stopWatch.stop();
		redisInfo.setResponseTime(stopWatch.getTotalTimeMillis());
		redisInfo.setTotalKeys(jedis.keys("*").size());
		return redisInfo;
	}

	private static<T> T getRedisInfo(Jedis jedis, Class<T> clazz) {
		Map<String, String> info = getInfo(jedis);
		return JSONObject.parseObject(JSON.toJSONString(info), clazz);
	}

	public static List<RedisMonitorNodeInfo> getRedisMonitor(RedisNode nodeInfo) {
		ArrayList<RedisMonitorNodeInfo> list = new ArrayList<>();
		getNodeList(nodeInfo).forEach(item -> {
			Jedis jedis = create(item.getHost(), item.getPort(), nodeInfo.getPassword());
			StopWatch watch = new StopWatch();
			watch.start();
			RedisMonitorNodeInfo redisMonitor = getRedisInfo(jedis, RedisMonitorNodeInfo.class);
			watch.stop();
			long responseTime = watch.getTotalTimeMillis();
			long currentTime = System.currentTimeMillis();
			redisMonitor.setResponseTime(responseTime);
			redisMonitor.setCreateTime(currentTime);
			redisMonitor.setIp(item.getHost() + ":" + item.getPort());
			redisMonitor.setTotalKeys(jedis.keys("*").size());
			redisMonitor.setId(SnowFakeIdUtil.snowFakeId());
			list.add(redisMonitor);
		});
		return list;
	}

	public static void main(String[] args) {
		Jedis jedis = create("127.0.0.1", 6379);
		String info = jedis.info();
	}


}

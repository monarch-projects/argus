package org.titan.argus.example.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;
import org.titan.argus.example.redis.es.RedisNodeInfo;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class JedisUtil {
	public static Jedis create(String host, Integer port, String password) {
		Jedis jedis = new Jedis(host, port);
		if (StringUtils.isNotBlank(password)) {
			jedis.auth(password);
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

	public static RedisNodeSimpleInfo nodeInfo(org.titan.argus.example.redis.RedisNodeInfo info) {
		Jedis jedis = create(info.getHost(), info.getPort(), info.getPassword());
		String name = getRedisModeCode(jedis);
		RedisNodeSimpleInfo simpleInfo = new RedisNodeSimpleInfo();
		Map<String, String> map = getInfo(jedis);
		if (RedisModeEnum.CLUSTER.getName().equals(name)) {
			String[] strings = jedis.clusterNodes().split("\n");
			simpleInfo.setNodeSize(strings.length).setRole(
					Arrays.stream(strings)
							.filter(item -> item.contains(String.valueOf(info.getPort())))
							.map(item -> item.contains("master") ? "master" : "slave")
							.findFirst()
							.orElse(null)
			);
		} else {
			simpleInfo.setMode(name).setNodeSize(1).setRole(map.get("role"));
		}
		simpleInfo.setHost(info.getHost()).setVersion(map.get("redis_version")).setPort(Integer.valueOf(map.get("tcp_port")));
		return simpleInfo;
	}

	public static List<RedisNodeMetadataInfo> getNodeList(org.titan.argus.example.redis.RedisNodeInfo nodeInfo) {
		Jedis jedis = create(nodeInfo.getHost(), nodeInfo.getPort(), nodeInfo.getPassword());
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
							.host(nodeInfo.getHost())
							.port(nodeInfo.getPort())
							.role(info.get("role"))
							.build()
			);
		}
		return list;
	}

	public static RedisNodeMetadataInfo metadataInfo(org.titan.argus.example.redis.RedisNodeInfo info) {
		return null;
	}

	public static List<RedisNodeInfo> getRedisMonitor(org.titan.argus.example.redis.RedisNodeInfo nodeInfo) {
		ArrayList<RedisNodeInfo> list = Lists.newArrayList();
		getNodeList(nodeInfo).forEach(item -> {
			StopWatch watch = new StopWatch();
			watch.start();
			Jedis jedis = create(item.getHost(), item.getPort(), nodeInfo.getPassword());
			Map<String, String> info = getInfo(jedis);
			watch.stop();
			long responseTime = watch.getTotalTimeMillis();
			RedisNodeInfo redisMonitor = JSONObject.parseObject(JSON.toJSONString(info), RedisNodeInfo.class);
			redisMonitor.setResponseTime(responseTime);
			list.add(redisMonitor);
		});
		return list;
	}


}

package org.titan.argus.example.redis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
public class RedisService {
	public void test() {
	}

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.223", 6381);
		String s = jedis.clusterInfo();
		String s1 = jedis.clusterNodes();
		List<String> list = jedis.configGet("*");
		String info = jedis.info();
		Map map = JSONObject.parseObject(s, Map.class);
		map.forEach((k, v) -> {
			System.out.println("key is " + k);
			System.out.println("value is " + v);
		});
	}
}

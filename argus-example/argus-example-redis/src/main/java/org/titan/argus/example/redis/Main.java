package org.titan.argus.example.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
//		Jedis jedis = new Jedis("192.168.1.222", 16379);
		Jedis jedis = new Jedis("192.168.1.222", 26379);
		String info = jedis.info();

		List<Map<String, String>> maps = jedis.sentinelMasters();
		List<Map<String, String>> mymaster = jedis.sentinelSlaves("mymaster");
	}
}

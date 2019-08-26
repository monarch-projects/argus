package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "redis")
public class ArgusRedisEndpoint {
	@Autowired
	private RedisTemplate redisTemplate;

	@GetMapping("/info")
	public Object getRedisAllInfo() {
		return redisTemplate.execute((RedisCallback) con -> con.info());
	}

	@GetMapping("/used/memory")
	public Object getRedisUsedMemoryInfo() {
		return redisTemplate.execute((RedisCallback) con -> con.info("memory").get("used_memory"));
	}

	@GetMapping("/resources")
	public Map<Object, Object> getAllResources() {
		Set keys = redisTemplate.keys("*");
		Map map = new HashMap(keys.size());
		keys.forEach(key -> {
			map.put(key, redisTemplate.opsForValue().get(key));
		});
		return map;
	}

	@GetMapping("/config")
	public Object getRedisConfigArgs() {
		return redisTemplate.execute((RedisCallback)con -> con.getConfig("*"));
	}

	@PostMapping("/config")
	public Object changeRedisConfigArgs(@RequestBody Map<String, String> map) {
		Map<String, String> temp = new HashMap<>(map);
		return redisTemplate.execute((RedisCallback) con -> {
			map.forEach((k, v) -> {
				try {
					con.setConfig(k, v);
				} catch (Exception ex) {
					temp.remove(k);
				}
			});
			return temp;
		});
	}
}

package org.titan.argus.example.redis.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author starboyate
 */
@RestController
public class RedisMonitorController {
	@Autowired
	private RedisMonitorService redisMonitorService;
	@GetMapping
	public void test() {
		RedisNodeInfo save = redisMonitorService
				.save(RedisNodeInfo.builder().ip("192.168.1.223:6381").responseTime(1l).id(1l).build());
		RedisNodeInfo save1 = redisMonitorService
				.save(RedisNodeInfo.builder().ip("192.168.1.223:6381").responseTime(1l).id(2l).build());
		long count = redisMonitorService.count();
		List<RedisNodeInfo> byIp = redisMonitorService.findByIp("192.168.1.223:6381");
	}
}

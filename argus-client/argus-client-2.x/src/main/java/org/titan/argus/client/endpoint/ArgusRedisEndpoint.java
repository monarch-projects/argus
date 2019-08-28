package org.titan.argus.client.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.titan.argus.plugin.redis.core.RedisService;

import java.util.Map;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "redis")
public class ArgusRedisEndpoint {
	private final RedisService service;

	public ArgusRedisEndpoint(RedisService service) {
		this.service = service;
	}

	@GetMapping("/info")
	public Object getRedisAllInfo() {
		return this.service.getRedisAllInfo();
	}

	@GetMapping("/node")
	public Object getRedisNodeInfo() {
		return this.service.getRedisNodeInfo();
	}

	@GetMapping("/used/memory")
	public Object getRedisUsedMemoryInfo() {
		return this.service.getRedisUsedMemoryInfo();
	}

	@GetMapping("/resources")
	public Map<Object, Object> getAllResources() {
		return this.service.getAllResources();
	}

	@GetMapping("/config")
	public Object getRedisConfigArgs() {
		return this.service.getRedisConfigArgs();
	}

	@PutMapping("/config")
	public Object changeRedisConfigArgs(@RequestBody Map<String, String> map) {
		return this.service.changeRedisConfigArgs(map);
	}
}

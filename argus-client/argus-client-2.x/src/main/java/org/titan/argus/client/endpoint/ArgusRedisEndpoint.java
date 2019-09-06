package org.titan.argus.client.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.titan.argus.client.redis.RedisRepository;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "redis")
public class ArgusRedisEndpoint {
	private final RedisRepository repository;

	public ArgusRedisEndpoint(RedisRepository repository) {
		this.repository = repository;
	}


	@GetMapping("/node")
	public Object getRedisNodeInfo() {
		return this.repository.getRedisNode();
	}

}

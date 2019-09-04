package org.argus.example.eureka.client;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.reactive.WebFluxEndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClusterConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TestController {
	@Autowired
	private RedisTemplate redisTemplate;




	@GetMapping("/aaa")
	public void test() {
		RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
		String hostName = ((LettuceConnectionFactory) connectionFactory).getHostName();
		RedisConnection connection1 = connectionFactory.getConnection();
		Object nativeConnection = connection1.getNativeConnection();
		String clientName = connection1.getClientName();
		List<RedisClientInfo> clientList = connection1.getClientList();
		Properties info = connection1.info();
		Properties config = connection1.getConfig("*");
		info.forEach((k ,v) -> {
			System.err.println("k is " + k);
			System.err.println("v is " + v);
		});
		RedisSentinelConnection sentinelConnection = connectionFactory.getSentinelConnection();
		RedisClusterConnection connection = connectionFactory.getClusterConnection();
	}
}

package org.argus.example.eureka.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import javassist.NotFoundException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.plugin.fallback.hystrix.core.ArgusHystrixCommandConvert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
public class HelloController {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	public String fallback() {
		return "error";
	}

	@GetMapping("/all")
	public List<String> all() throws NotFoundException, ClassNotFoundException, IllegalAccessException {
		List<String> list = new ArrayList<>();
		Class<?> aClass = Class.forName(HystrixPropertiesManager.class.getName());
		Field[] fields = aClass.getFields();
		for (Field field : fields) {
			String a = (String) field.get(field.getName());
			list.add(a);
		}
		return list;
	}

	@GetMapping("test")
	public Object hehe(){
		return redisTemplate.execute((RedisCallback) con -> con.info()
		);
	}

	@GetMapping("test2")
	public Object test() {
		return redisTemplate.keys("*");
	}


}
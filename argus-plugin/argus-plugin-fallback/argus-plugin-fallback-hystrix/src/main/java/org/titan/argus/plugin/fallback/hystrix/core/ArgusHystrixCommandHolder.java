package org.titan.argus.plugin.fallback.hystrix.core;

import com.netflix.hystrix.HystrixCommandProperties;
import lombok.Data;
import org.springframework.cloud.netflix.hystrix.HystrixProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
@Component
@Data
public class ArgusHystrixCommandHolder {
	private HystrixCommandProperties properties;


	/**
	 * TODO: HystrixCommandProperties convert to Map<String, String>
	 */
	public void convert() {

	}
}

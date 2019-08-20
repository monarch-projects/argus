package org.titan.argus.example.gateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.plugin.fallback.hystrix.core.ArgusHystrixProperties;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

@RestController
public class HelloController {

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/hello")
	public Mono<String> hello(@RequestBody ArgusHystrixProperties properties) {
		return Mono.justOrEmpty("hello");
	}

	public Mono<String> fallback(@RequestBody ArgusHystrixProperties properties) {
		return Mono.justOrEmpty("error");
	}

	public static void main(String[] args) throws NoSuchMethodException {
		Method[] methods = HelloController.class.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
			System.out.println("type name" + method.getReturnType().getName());
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (Class clazz : parameterTypes) {
				System.out.println(clazz.getName());
			}
		}
//		Method hello = HelloController.class.getMethod("hello");
//		System.out.println(hello.getName());
	}
}

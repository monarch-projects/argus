package org.argus.example.eureka.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.plugin.fallback.hystrix.core.ArgusHystrixCommandConvert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@RestController
public class HelloController {


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
	public void hehe(){
	}

}
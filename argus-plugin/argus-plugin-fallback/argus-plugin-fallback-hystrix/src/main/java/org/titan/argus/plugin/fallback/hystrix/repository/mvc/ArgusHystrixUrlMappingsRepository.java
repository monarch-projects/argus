package org.titan.argus.plugin.fallback.hystrix.repository.mvc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.titan.argus.plugin.fallback.common.core.ArgusUrlMappingsRepository;
import org.titan.argus.plugin.fallback.common.entities.ArgusUrlMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author starboyate
 */
public class ArgusHystrixUrlMappingsRepository implements ArgusUrlMappingsRepository {
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Override
	public Set<ArgusUrlMapping> getAllUrlMappings() {
		Set<ArgusUrlMapping> argusUrlMappingSet = new HashSet<>();
		String tempClassName = null;
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			String url = null;
			for (String tempUrl : p.getPatterns()) {
				url = tempUrl;
			}
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			String type = methodsCondition.toString();
			if (type.startsWith("[") && type.endsWith("]")) {
				type = type.substring(1, type.length() - 1);
			}
			Method item  = method.getMethod();
			if (tempClassName == null || !tempClassName.equals(item.getDeclaringClass().getName())) {
				argusUrlMappingSet.add(ArgusUrlMapping.builder()
						.methodName(item.toString())
						.className(item.getDeclaringClass().getName())
						.type(type)
						.url(url)
						.isFallback(item.getAnnotation(HystrixCommand.class) != null)
						.build());
			}
			tempClassName = item.getDeclaringClass().getName();
		}
		return argusUrlMappingSet;
	}
}

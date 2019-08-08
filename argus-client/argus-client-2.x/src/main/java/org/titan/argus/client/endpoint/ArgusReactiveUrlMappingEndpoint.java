package org.titan.argus.client.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.condition.PatternsRequestCondition;
import org.springframework.web.reactive.result.condition.RequestMethodsRequestCondition;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
@Endpoint(id = "urlMappings")
public class ArgusReactiveUrlMappingEndpoint {
	@Resource(type = RequestMappingHandlerMapping.class)
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@ReadOperation
	public List<Map<String, String>> getAllMappings() {
		List<Map<String, String>> urlList = new ArrayList<Map<String, String>>();

		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			for (PathPattern pathPattern : p.getPatterns()) {
				hashMap.put("url", pathPattern.toString());
			}
			hashMap.put("className", method.getMethod().getDeclaringClass().getName());
			hashMap.put("method", method.getMethod().getName());
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			String type = methodsCondition.toString();
			if (type != null && type.startsWith("[") && type.endsWith("]")) {
				type = type.substring(1, type.length() - 1);
				hashMap.put("type", type);
			}
			urlList.add(hashMap);
		}
		return urlList;
	}
}

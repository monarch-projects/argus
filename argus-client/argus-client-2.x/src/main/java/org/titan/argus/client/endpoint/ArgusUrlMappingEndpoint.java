package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
@Endpoint(id = "urlMappings")
public class ArgusUrlMappingEndpoint {

	@Autowired
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
			for (String url : p.getPatterns()) {
				hashMap.put("url", url);
			}
			hashMap.put("className", method.getMethod().getDeclaringClass().getName());
			hashMap.put("method", method.getMethod().getName());
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			String type = methodsCondition.toString();
			if (type.startsWith("[") && type.endsWith("]")) {
				type = type.substring(1, type.length() - 1);
				hashMap.put("type", type);
			}
			urlList.add(hashMap);
		}
		return urlList;
	}


}

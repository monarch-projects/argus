package org.titan.argus.discovery.eureka.repository;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.argus.discovery.common.entities.ArgusServiceApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author starboyate
 */
@Component
public class ArgusEurekaAppRepository {
	@Autowired
	private EurekaClient client;



	public Map<String, ArgusServiceApp> getAllApp() {

		List<Application> applications = client.getApplications().getRegisteredApplications();
		Map<String, ArgusServiceApp> map = new HashMap<>(applications.size());
		applications.forEach(item -> {
			map.put(item.getName(), ArgusServiceApp.builder().appName(item.getName()).size(item.size()).build());
		});
		return map;
	}



}

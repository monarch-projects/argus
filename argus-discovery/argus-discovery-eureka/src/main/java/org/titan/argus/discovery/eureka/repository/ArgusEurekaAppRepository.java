package org.titan.argus.discovery.eureka.repository;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.eureka.entities.ArgusEurekaServiceApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author starboyate
 */
@Component
public class ArgusEurekaAppRepository {
	@Autowired
	private EurekaClient eurekaClient;



	public Map<String, ArgusEurekaServiceApp> getAllApp() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		Map<String, ArgusEurekaServiceApp> map = new HashMap<>(applications.size());
		applications.forEach(item -> {
			map.put(item.getName(), ArgusEurekaServiceApp.builder().appName(item.getName()).size(item.size()).build());
		});
		return map;
	}



}

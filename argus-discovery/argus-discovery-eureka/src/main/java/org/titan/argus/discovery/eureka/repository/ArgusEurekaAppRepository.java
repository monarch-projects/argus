package org.titan.argus.discovery.eureka.repository;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusServiceApp;
import org.titan.argus.discovery.common.repository.AppRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author starboyate
 */
@Component
public class ArgusEurekaAppRepository implements AppRepository {
	@Autowired
	private EurekaClient client;


	@Override
	public List<ArgusServiceApp> findAll() {
		List<Application> applications = client.getApplications().getRegisteredApplications();
		List<ArgusServiceApp> list = new ArrayList<>(applications.size());
		applications.forEach(item -> {
			list.add(ArgusServiceApp.builder().appName(item.getName()).size(item.size()).build());
		});
		return list;
	}
}

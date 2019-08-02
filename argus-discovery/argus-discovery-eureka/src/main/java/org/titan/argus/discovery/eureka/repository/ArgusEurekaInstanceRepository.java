package org.titan.argus.discovery.eureka.repository;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.argus.discovery.common.entities.ArgusInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/**
 * @author starboyate
 *
 */
@Component
public class ArgusEurekaInstanceRepository {
	@Autowired
	private EurekaClient eurekaClient;



	/**
	 * get all instances
	 * @return Map<String, List<ArgusEurekaInstance>>
	 */
	public Map<String, List<ArgusInstance>> getAllInstances() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		Map<String, List<ArgusInstance>> map = new HashMap<>(applications.size());
		applications.forEach(application -> {
			List<ArgusInstance> argusEurekaInstances = new ArrayList<>(application.size());
			application.getInstances().forEach(item -> {
				argusEurekaInstances.add(
						ArgusInstance.builder()
						.appName(item.getAppName())
						.appGroup(item.getAppGroupName())
						.host(item.getHostName())
						.port(item.getPort())
						.instanceId(item.getId())
						.status(item.getStatus().name())
						.build()
				);
			});
			map.put(application.getName(), argusEurekaInstances);
		});
		return map;
	}

	/**
	 * get instance by app name
	 * @param appName
	 * @return List<ArgusEurekaInstance>
	 * @throws ExecutionException
	 */
	public List<ArgusInstance> getInstanceByAppName(String appName) throws ExecutionException {
		Assert.notNull(appName, "app name must be not null");
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		for (Application application : applications) {
			if (application.getName().equals(appName)) {
				return application.getInstances().stream()
						.map(item -> ArgusInstance.builder().appName(item.getAppName())
								.appGroup(item.getAppGroupName()).host(item.getHostName()).port(item.getPort())
								.status(item.getStatus().name()).instanceId(item.getId()).build())
						.collect(Collectors.toList());
			}
		}
		return null;
	}

	/**
	 * remove instance
	 * @param appName
	 * @param instanceId
	 */
	public void removeInstance(String appName, String instanceId) {
		Application application = eurekaClient.getApplication(appName);
		InstanceInfo instanceInfo = application.getByInstanceId(instanceId);
		application.removeInstance(instanceInfo);
	}

	public void register() {

	}





}

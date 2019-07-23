package org.titan.argus.discovery.eureka.repository;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author starboyate
 *
 */
public class ArgusEurekaRepository {
	@Autowired
	EurekaClient eurekaClient;

	public void getAll() {
		Applications applications = eurekaClient.getApplications();

	}

}

package org.titan.argus.discovery.eureka.repository;

import com.netflix.appinfo.EurekaInstanceConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author starboyate
 *
 */
public class ArgusEurekaRepository {
	@Autowired
	EurekaInstanceConfig eurekaInstanceConfig;

}

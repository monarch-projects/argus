package org.titan.argus.client.endpoint;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.titan.argus.client.entities.ArgusVersion;


/**
 * @author starboyae
 *  获取服务的spring boot、spring cloud 、服务本身的版本信息
 */
@Endpoint(id = "version")
public class ArgusVersionEndpoint {
	@ReadOperation
	public ArgusVersion getVersion() {
		return ArgusVersion.builder().springBootVersion(SpringBootVersion.getVersion()).build();
	}

}

package org.titan.argus.client.endpoint;

import fr.dutra.tools.maven.deptree.core.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.servlet.ControllerEndpointHandlerMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.titan.argus.client.entities.metadata.ArgusClientMetadataInfo;
import org.titan.argus.client.pom.dependency.ArgusDependencyAnalyzer;
import java.util.Map;

/**
 * @author starboyate
 */
@Endpoint(id = "metaInfo")
public class ArgusMetaInfoMvcEndpoint {
	private Logger logger = LoggerFactory.getLogger(ArgusMetaInfoMvcEndpoint.class);

	@Autowired
	private ControllerEndpointHandlerMapping handlerMapping;




	@ReadOperation
	public ArgusClientMetadataInfo getMetadataInfo() {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.handlerMapping.getHandlerMethods();
		ArgusClientMetadataInfo build = new ArgusClientMetadataInfo();
		handlerMethods.forEach((k, v) -> {
			if (k.toString().contains("routes")) {
				build.setIsGateway(Boolean.TRUE);
			}
			if (k.toString().contains("mongodb")) {
				build.setIsUsedMongodb(Boolean.TRUE);
			}
			if (k.toString().contains("redis")) {
				build.setIsUsedRedis(Boolean.TRUE);
			}
		});
		Node node = ArgusDependencyAnalyzer.analysis();
		build.setArtifactId(node.getArtifactId());
		build.setGroupId(node.getGroupId());
		build.setVersion(node.getVersion());
		return build;
	}



}

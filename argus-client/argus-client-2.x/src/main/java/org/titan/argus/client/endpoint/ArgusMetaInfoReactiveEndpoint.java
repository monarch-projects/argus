package org.titan.argus.client.endpoint;

import fr.dutra.tools.maven.deptree.core.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.reactive.ControllerEndpointHandlerMapping;
import org.titan.argus.client.entities.metadata.ArgusClientMetadataInfo;
import org.titan.argus.client.pom.dependency.ArgusDependencyAnalyzer;


@Endpoint(id = "metaInfo")
public class ArgusMetaInfoReactiveEndpoint {
	private Logger logger = LoggerFactory.getLogger(ArgusMetaInfoMvcEndpoint.class);

	@Autowired
	private ControllerEndpointHandlerMapping handlerMapping;



	@ReadOperation
	public ArgusClientMetadataInfo getMetadataInfo() {
		ArgusClientMetadataInfo build = new ArgusClientMetadataInfo();
		this.handlerMapping.getHandlerMethods().forEach((k, v) -> {
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

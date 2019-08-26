package org.titan.argus.client.endpoint;

import fr.dutra.tools.maven.deptree.core.Node;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.reactive.ControllerEndpointHandlerMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.titan.argus.client.entities.metadata.ArgusClientMetadataInfo;
import org.titan.argus.client.pom.ArgusPomAnalyzer;
import org.titan.argus.client.pom.dependency.ArgusDependencyAnalyzer;

import java.util.Map;

@Endpoint(id = "metaInfo")
public class ArgusMetaInfoReactiveEndpoint {
	@Autowired
	private ControllerEndpointHandlerMapping handlerMapping;

	@ReadOperation
	public ArgusClientMetadataInfo getMetadataInfo() {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.handlerMapping.getHandlerMethods();
		ArgusClientMetadataInfo build = new ArgusClientMetadataInfo();
		handlerMethods.forEach((k, v) -> {
			if (k.toString().contains("routes")) {
				build.setIsGateway(true);
			}
		});
		Node node = ArgusDependencyAnalyzer.analysis();
		build.setArtifactId(node.getArtifactId());
		build.setGroupId(node.getGroupId());
		build.setVersion(node.getVersion());
		return build;
	}
}

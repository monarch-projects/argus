package org.titan.argus.client.endpoint;

import fr.dutra.tools.maven.deptree.core.Node;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.titan.argus.client.pom.dependency.ArgusDependencyAnalyer;


/**
 * @author starboyate
 */
@Endpoint(id = "dependency")
public class ArgusDependencyEndpoint {

	@ReadOperation
	public Node getAllDependencies() {
		return ArgusDependencyAnalyer.analysis();
	}
}

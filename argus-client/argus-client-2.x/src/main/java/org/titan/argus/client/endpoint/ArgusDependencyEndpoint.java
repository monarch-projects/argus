package org.titan.argus.client.endpoint;

import fr.dutra.tools.maven.deptree.core.Node;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.titan.argus.client.pom.dependency.ArgusDependencyAnalyzer;



/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "dependency")
public class ArgusDependencyEndpoint {

	@GetMapping
	public Node getAllDependencies() {

		return ArgusDependencyAnalyzer.analysis();
	}
}

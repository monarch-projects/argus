package org.titan.argus.client.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.titan.argus.client.mongodb.MongodbRepository;
import org.titan.argus.plugin.mongodb.core.MongodbService;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "mongodb")
public class ArgusMongodbEndpoint {
	private final MongodbRepository repository;

	public ArgusMongodbEndpoint(MongodbRepository repository) {
		this.repository = repository;
	}
	@GetMapping("/node")
	public Object getMongodbNodeInfo() {
		return this.repository.getNode();
	}




}

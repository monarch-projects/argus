package org.titan.argus.client.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.titan.argus.plugin.mongodb.core.MongodbService;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "mongodb")
public class ArgusMongodbEndpoint {
	private final MongodbService service;

	public ArgusMongodbEndpoint(MongodbService service) {
		this.service = service;
	}
	@GetMapping("/node")
	public Object getMongodbNodeInfo() {
		return this.service.getNode();
	}

	@GetMapping("/databases")
	public Object getAllDataBaseInfo() {
		return this.service.getAllDataBaseInfo();
	}

	@GetMapping("/namespaces")
	public Object getAllNamespaces() {
		return this.service.getAllNamespaces();
	}

	@GetMapping("/os")
	public Object getOsInfo() {
		return this.service.getOsInfo();
	}

	@GetMapping("/lock")
	public Object getMongodbLockInfo() {
		return this.service.getMongodbLockInfo();
	}

	@GetMapping("/replicationStatus")
	public Object getMongodbReplicationStatusInfo() {
		return this.service.getMongodbReplicationStatusInfo();
	}

	@GetMapping("/replicationConfig")
	public Object getMongodbReplicationConfig() {
		return this.service.getMongodbReplicationConfig();
	}

	@GetMapping("/collection/system")
	public Object getCollectionSystemInfo(@RequestParam("dataBase") String dataBase, @RequestParam("collectionName") String collectionName) {
		return this.service.getCollectionSystemInfo(dataBase, collectionName);
	}



}

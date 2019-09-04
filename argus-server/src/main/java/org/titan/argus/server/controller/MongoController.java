package org.titan.argus.server.controller;

import com.google.common.collect.Sets;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.core.MiddleWareNodeHolder;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.server.response.ObjectDataResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/mongodb")
public class MongoController extends BaseController {
	private Set<InstanceMetadata> metadataSet = Sets.newHashSet();

	@GetMapping("/node")
	public ObjectCollectionResponse getMongodbNodeInfo() {
		return new ObjectCollectionResponse<>(MiddleWareNodeHolder.getMongodbNodeInfoSet());
	}

	@GetMapping("/{id}/databases")
	public ObjectDataResponse getAllDataBaseInfo(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.MONGODB_DATABASES, metadata.getId());
	}

	@GetMapping("/{id}/namespaces")
	public ObjectDataResponse getAllNamespaces(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.MONGODB_NAMESPACES, metadata.getId());
	}

	@GetMapping("/{id}/os")
	public ObjectDataResponse getOsInfo(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.MONGODB_OS, metadata.getId());
	}

	@GetMapping("/{id}/lock")
	public ObjectDataResponse getMongodbLockInfo(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.MONGODB_LOCK, metadata.getId());
	}

	@GetMapping("/{id}/replicationStatus")
	public ObjectDataResponse getMongodbReplicationStatusInfo(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.MONGODB_REPLICATION_STATUS, metadata.getId());
	}

	@GetMapping("/{id}/replicationConfig")
	public ObjectDataResponse getMongodbReplicationConfig(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.MONGODB_REPLICATION_CONFIG, metadata.getId());
	}

	@GetMapping("/{id}/collection/system")
	public ObjectDataResponse getCollectionSystemInfo(@PathVariable String id, @RequestParam("dataBase") String dataBase, @RequestParam("collectionName") String collectionName) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		Map<String, Object> map = new HashMap<>();
		map.put("dataBase", dataBase);
		map.put("collectionName", collectionName);
		return proxyGet(ArgusActuatorConstant.MONGODB_COLLECTION_SYSTEM, metadata.getId(), map);
	}
}

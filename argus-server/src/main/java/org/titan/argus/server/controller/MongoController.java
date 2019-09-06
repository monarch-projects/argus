package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.MongodbService;
import org.titan.argus.tools.monitor.mongodb.core.MongodbNodeHolder;
import org.titan.argus.tools.monitor.mongodb.domain.MongodbNode;


/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/mongodb")
public class MongoController extends BaseController {
	@Autowired
	private MongodbService mongodbService;

	@GetMapping("/node")
	public ObjectCollectionResponse getMongodbNodeInfo() {
		return new ObjectCollectionResponse<>(mongodbService.getAllMongodbNodeList());
	}

	@GetMapping("/{id}/metric")
	public ObjectDataResponse getMongodbMetric(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getMongodbMetricInfo(mongodbNode));
	}

	@GetMapping("/{id}/globalLock")
	public ObjectDataResponse getMongodbGlobalLockInfo(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getMongodbGlobalLockInfo(mongodbNode));
	}

	@GetMapping("/{id}/session")
	public ObjectDataResponse getMongodbSessionInfo(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getMongodbSessionInfo(mongodbNode));
	}
	@GetMapping("/{id}/repl")
	public ObjectDataResponse getMongodbReplInfo(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getMongodbReplInfo(mongodbNode));
	}

	@GetMapping("/{id}/storageEngineInfo")
	public ObjectDataResponse getMongodbStorageEngineInfo(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getMongodbStorageEngineInfo(mongodbNode));
	}

	@GetMapping("/{id}/os")
	public ObjectDataResponse getOsInfo(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getMongodbOSInfo(mongodbNode));
	}

	@GetMapping("/{id}/databases")
	public ObjectDataResponse getAllDataBaseInfo(@PathVariable Long id) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getAllDataBaseInfo(mongodbNode));
	}

	@GetMapping("/{id}/{database}/{collectionName}/systemInfo")
	public ObjectDataResponse getCollectionSystemInfo(@PathVariable Long id, @PathVariable String dataBase, @PathVariable String collectionName) {
		MongodbNode mongodbNode = MongodbNodeHolder.get(id);
		return new ObjectDataResponse<>(mongodbService.getCollectionSystemInfo(mongodbNode, dataBase, collectionName));
	}
}

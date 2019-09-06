package org.titan.argus.tools.monitor.mongodb.core;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoIterable;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.titan.argus.storage.es.domain.MongodbMonitorNodeInfo;
import org.titan.argus.tools.monitor.mongodb.domain.*;
import org.titan.argus.util.HexTransformationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author starboyate
 */
public class MongodbRepository {
	private static final Logger logger = LoggerFactory.getLogger(MongoClient.class);

	private static final String URI_PREFIX = "mongodb://";

	private static final Cache<String, MongoClient> MONGO_CLIENT_CACHE;

	private static final Integer MAX_SIZE = 50;

	private static final Integer EXPIRE_TIME = 10;

	static {
		MONGO_CLIENT_CACHE = CacheBuilder.newBuilder()
				.maximumSize(MAX_SIZE)
				.expireAfterAccess(EXPIRE_TIME, TimeUnit.MINUTES)
				.removalListener((RemovalNotification<String, MongoClient> notification) -> {
					notification.getValue().close();
					logger.info("mongclient close, client is " + notification.getKey());
				})
				.build();
	}
	public static MongoClient create(String host, Integer port, String userName, String password) {
		String uri = null;
		if (StringUtils.isBlank(userName) && StringUtils.isBlank(password)) {
			uri = URI_PREFIX + host + ":" + port;
		} else {
			uri = URI_PREFIX + userName + ":" + password + "@" + host + ":" + port;
		}
		MongoClient client = MONGO_CLIENT_CACHE.getIfPresent(uri);
		if (null == client) {
			MongoClientURI mongoClientURI = new MongoClientURI(uri);
			client = new MongoClient(mongoClientURI);
			MONGO_CLIENT_CACHE.put(uri, client);
		}
		return client;
	}

	public static Document getServerStatus(MongodbNode node) {
		MongoClient mongoClient = create(node.getHost(), node.getPort(), node.getUserName(), node.getPassword());
		return mongoClient.getDatabase("admin").runCommand(new Document("serverStatus", 1));
	}


	public static MongodbMemInfo getMem(MongodbNode mongodbNode) {
		Document mem = getServerStatus(mongodbNode).get("mem", Document.class);
		return JSONObject.parseObject(mem.toJson(), MongodbMemInfo.class);
	}

	public static MongodbOpcountersInfo getOpcounters(MongodbNode mongodbNode) {
		Document opcounters = getServerStatus(mongodbNode).get("opcounters", Document.class);
		return JSONObject.parseObject(opcounters.toJson(), MongodbOpcountersInfo.class);
	}

	public static MongodbClientConnectionsInfo getClientConnections(MongodbNode mongodbNode) {
		Document serverStatus = getServerStatus(mongodbNode);
		Document connections = serverStatus.get("connections", Document.class);
		Document activeClients = serverStatus.get("globalLock", Document.class).get("activeClients", Document.class);
		MongodbClientConnectionsInfo mongodbClientConnectionsInfo = JSONObject
				.parseObject(connections.toJson(), MongodbClientConnectionsInfo.class);
		mongodbClientConnectionsInfo.setActiveClientsReaders(activeClients.getInteger("readers")).setActiveClientsWriters(activeClients.getInteger("writers"));
		return mongodbClientConnectionsInfo;
	}

	public static MongodbNetworkInfo getNetwork(MongodbNode mongodbNode) {
		Document network = getServerStatus(mongodbNode).get("network", Document.class);
		return MongodbNetworkInfo.builder()
				.bytesIn(network.getLong("bytesIn"))
				.bytesOut(network.getLong("bytesOut"))
				.numRequests(network.getLong("numRequests"))
				.build();
	}

	public static MongodbGlobalLockInfo getGlobalLock(MongodbNode mongodbNode) {
		Document globalLock = getServerStatus(mongodbNode).get("globalLock", Document.class);
		return MongodbGlobalLockInfo.builder()
				.totalTime(globalLock.getLong("totalTime"))
				.mongodbGlobalLockActiveClientsInfo(getGlobalLockActiveClients(globalLock))
				.mongodbGlobalLockCurrentQueueInfo(getGlobalLockCurrentQueue(globalLock))
				.build();
	}

	private static MongodbGlobalLockCurrentQueueInfo getGlobalLockCurrentQueue(Document globalLock) {
		Document currentQueue = globalLock.get("currentQueue", Document.class);
		return JSONObject.parseObject(currentQueue.toJson(), MongodbGlobalLockCurrentQueueInfo.class);
	}

	private static MongodbGlobalLockActiveClientsInfo getGlobalLockActiveClients(Document globalLock) {
		Document activeClients = globalLock.get("activeClients", Document.class);
		return JSONObject.parseObject(activeClients.toJson(), MongodbGlobalLockActiveClientsInfo.class);
	}

	public static MongodbLogicalSessionRecordCacheInfo getSession(MongodbNode node) {
		Document logicalSessionRecordCache = getServerStatus(node).get("logicalSessionRecordCache", Document.class);
		return JSONObject.parseObject(logicalSessionRecordCache.toJson(), MongodbLogicalSessionRecordCacheInfo.class);
	}

	public static MongodbReplInfo getRepl(MongodbNode node) {
		Document repl = getServerStatus(node).get("metrics", Document.class).get("repl", Document.class);
		return MongodbReplInfo.builder()
				.mongodbRelicationApplyInfo(getReplApply(repl))
				.mongodbRelicationBufferInfo(getReplBuffer(repl))
				.mongodbReplicationNetworkInfo(getReplNetwork(repl))
				.build();
	}

	private static MongodbReplApplyInfo getReplApply(Document repl) {
		Document apply = repl.get("apply", Document.class);
		return MongodbReplApplyInfo.builder()
				.batchSize(apply.getLong("batchSize"))
				.mongodbReplicationApplyBatchesInfo(getReplApplyBatches(apply))
				.build();
	}

	private static MongodbReplApplyInfo.MongodbReplicationApplyBatchesInfo getReplApplyBatches(Document apply) {
		Document batches = apply.get("batches", Document.class);
		return JSONObject.parseObject(batches.toJson(), MongodbReplApplyInfo.MongodbReplicationApplyBatchesInfo.class);
	}

	private static MongodbReplBufferInfo getReplBuffer(Document repl) {
		Document buffer = repl.get("buffer", Document.class);
		return MongodbReplBufferInfo.builder()
				.count(buffer.getLong("count"))
				.maxSizeBytes(buffer.getLong("maxSizeBytes"))
				.sizeBytes(buffer.getLong("sizeBytes"))
				.build();
	}

	private static MongodbReplNetworkInfo getReplNetwork(Document repl) {
		Document network = repl.get("network", Document.class);
		return MongodbReplNetworkInfo.builder()
				.bytes(network.getLong("bytes"))
				.ops(network.getLong("ops"))
				.readersCreated(network.getLong("readersCreated"))
				.mongodbReplicationNetworkGetMoreInfo(getReplNetworkGetMores(network))
				.build();
	}

	private static MongodbReplNetworkInfo.MongodbReplicationNetworkGetMoreInfo getReplNetworkGetMores(Document network) {
		Document getmores = network.get("getmores", Document.class);
		return JSONObject.parseObject(getmores.toJson(), MongodbReplNetworkInfo.MongodbReplicationNetworkGetMoreInfo.class);
	}

	public static MongodbStorageEngineInfo getStorageEngine(MongodbNode node) {
		Document storageEngine = getServerStatus(node).get("storageEngine", Document.class);
		return JSONObject.parseObject(storageEngine.toJson(), MongodbStorageEngineInfo.class);
	}

	public static MongodbOSInfo getOSInfo(MongodbNode node) {
		MongoClient mongoClient = create(node.getHost(), node.getPort(), node.getUserName(), node.getPassword());
		Document hostInfo = mongoClient.getDatabase("admin").runCommand(new BasicDBObject("hostInfo", 1));
		Document system = hostInfo.get("system", Document.class);
		Document os = hostInfo.get("os", Document.class);
		return MongodbOSInfo.builder()
				.name(os.getString("name"))
				.version(os.getString("version"))
				.cpuCoreSize(system.getInteger("numCores"))
				.memorySize(system.getInteger("memSizeMB").longValue())
				.build();
	}

	public static Boolean ping(MongodbNode node) {
		MongoClient mongoClient = create(node.getHost(), node.getPort(), node.getUserName(), node.getPassword());
		Document document = mongoClient.getDatabase("admin").runCommand(new BasicDBObject("ping", 1));
		if (document.getDouble("ok").equals(1.0)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static List<MongodbDataBase> getAllDataBaseInfo(MongodbNode node) {
		ArrayList<MongodbDataBase> list = Lists.newArrayList();
		MongoClient mongoClient = create(node.getHost(), node.getPort(), node.getUserName(), node.getPassword());
		Document document = mongoClient.getDatabase("admin").runCommand(new BasicDBObject("listDatabases", 1));
		List<Document> objects = document.get("databases", List.class);
		objects.forEach(item -> {

			MongoIterable<String> collectionNames = mongoClient.getDatabase(item.getString("name")).listCollectionNames();
			list.add(MongodbDataBase.builder().name(item.get("name").toString())
					.collections(Lists.newArrayList(collectionNames))
					.disk(HexTransformationUtils.formatSize(item.getDouble("sizeOnDisk").longValue())).build());
		});
		return list;
	}

	public static String getCollectionSystemInfo(MongodbNode node, String dataBase, String collectionName) {
		MongoClient mongoClient = create(node.getHost(), node.getPort(), node.getUserName(), node.getPassword());
		Document document = mongoClient.getDatabase(dataBase).runCommand(new Document("collStats", collectionName));
		return document.toJson();
	}

	public static MongodbMetricInfo getMetricInfo(MongodbNode mongodbNode) {
		MongodbMemInfo mem = getMem(mongodbNode);
		MongodbOpcountersInfo opcounters = getOpcounters(mongodbNode);
		MongodbClientConnectionsInfo clientConnections = getClientConnections(mongodbNode);
		MongodbNetworkInfo network = getNetwork(mongodbNode);
		return MongodbMetricInfo.builder()
				.mongodbClientConnectionsInfo(clientConnections)
				.mongodbMemInfo(mem)
				.mongodbOpcountersInfo(opcounters)
				.mongodbNetworkInfo(network)
				.build();
	}

	public static MongodbMetadataInfo getMetadataInfo(MongodbNode mongodbNode) {
		Document serverStatus = getServerStatus(mongodbNode);
		return MongodbMetadataInfo.builder()
				.host(serverStatus.getString("serverStatus"))
				.version(serverStatus.getString("version"))
				.upTime(serverStatus.getLong("uptime"))
				.build();
	}

	public static MongodbMonitorNodeInfo getMonitorNodeInfo(MongodbNode mongodbNode) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		MongodbMemInfo mem = getMem(mongodbNode);
		MongodbOpcountersInfo opcounters = getOpcounters(mongodbNode);
		MongodbClientConnectionsInfo clientConnections = getClientConnections(mongodbNode);
		MongodbNetworkInfo network = getNetwork(mongodbNode);
		MongodbMonitorNodeInfo mongodbMonitorNodeInfo = MongodbInfoConvert
				.convertToMongodbMonitorNodeInfo(clientConnections, network, opcounters, mem);
		MongodbMetadataInfo metadataInfo = getMetadataInfo(mongodbNode);
		stopWatch.stop();
		mongodbMonitorNodeInfo.setHost(metadataInfo.getHost())
				.setIp(mongodbNode.getHost() + ":" + mongodbNode.getPort())
				.setUptime(metadataInfo.getUpTime())
				.setVersion(metadataInfo.getVersion())
				.setResponseTime(stopWatch.getTotalTimeMillis());
		return mongodbMonitorNodeInfo;
	}

	public static void main(String[] args) throws InterruptedException {
		MongodbNode build = MongodbNode.builder().host("192.168.1.235").port(27017).build();

		MongoClient mongoClient = create(build.getHost(), build.getPort(), build.getUserName(), build.getPassword());
		MongodbMemInfo mem = getMem(build);
		Document serverStatus = getServerStatus(build);
		MongodbClientConnectionsInfo clientConnections = getClientConnections(build);
		MongodbOpcountersInfo opcounters = getOpcounters(build);
		MongodbNetworkInfo network = getNetwork(build);
		MongodbGlobalLockInfo globalLock = getGlobalLock(build);
		MongodbLogicalSessionRecordCacheInfo session = getSession(build);
		MongodbReplInfo repl = getRepl(build);
		MongodbStorageEngineInfo storageEngine = getStorageEngine(build);
		MongodbMonitorNodeInfo mongodbMonitorNodeInfo = MongodbInfoConvert
				.convertToMongodbMonitorNodeInfo(clientConnections, network, opcounters, mem);
		MongodbClientConnectionsInfo mongodbClientConnectionsInfo = MongodbInfoConvert
				.convertToClientConnection(mongodbMonitorNodeInfo);
		MongodbMemInfo mongodbMemInfo = MongodbInfoConvert.convertToMemInfo(mongodbMonitorNodeInfo);
		MongodbNetworkInfo mongodbNetworkInfo = MongodbInfoConvert.convertToNetwork(mongodbMonitorNodeInfo);
		MongodbOpcountersInfo mongodbOpcountersInfo = MongodbInfoConvert.convertToOpcounters(mongodbMonitorNodeInfo);
		MongodbOSInfo osInfo = getOSInfo(build);
		Boolean ping = ping(build);
		List<MongodbDataBase> allDataBaseInfo = getAllDataBaseInfo(build);
		String collectionSystemInfo = getCollectionSystemInfo(build, "g08", "user");
		System.out.println(MONGO_CLIENT_CACHE.size());
	}

}

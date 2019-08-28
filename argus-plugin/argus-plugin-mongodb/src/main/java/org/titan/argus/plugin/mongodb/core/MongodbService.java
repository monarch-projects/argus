package org.titan.argus.plugin.mongodb.core;



import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.titan.argus.plugin.mongodb.entities.MongodbDataBase;
import org.titan.argus.plugin.mongodb.entities.MongodbNode;
import org.titan.argus.plugin.mongodb.entities.MongodbOSInfo;
import org.titan.argus.plugin.mongodb.util.HexTransformationUtils;

import java.util.*;

/**
 * @author starboyate
 */
public class MongodbService {

	private final MongoClient client;

	private final MongoProperties properties;

	private final MongoTemplate template;

	private static final String MONGODB_URI_PREFIX = "mongodb://";


	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public MongodbService(MongoProperties properties, MongoClient client, MongoTemplate template) {
		this.properties = properties;
		this.client = client;
		this.template = template;
	}

	public MongodbNode getNode() {
		String host = this.properties.getHost();
		Integer port = this.properties.getPort();
		String userName = this.properties.getUsername();
		String password = null;
		if (this.properties.getPassword() != null) {
			password = new String(this.properties.getPassword()).intern();
		}
		String uri = this.properties.getUri();
		if (StringUtils.isBlank(host)) {
			host = uri.substring(MONGODB_URI_PREFIX.length() + 1, uri.lastIndexOf("/"));
		}
		if (port == null) {
			port = Integer.valueOf(uri.substring(uri.lastIndexOf(":") + 1, uri.lastIndexOf("/")));
		}

		Document document = this.template.getDb().runCommand(new Document("serverStatus", "admin"));
		return MongodbNode.builder().userName(userName).password(password).port(port).host(host).version(document.getString("version")).build();
	}

	public List<MongodbDataBase> getAllDataBaseInfo() {
		ArrayList<MongodbDataBase> list = Lists.newArrayList();
		Document document = this.client.getDatabase("admin")
				.runCommand(new BasicDBObject("listDatabases", 1));
		List<Document> objects = (List<Document>) document.get("databases");
		objects.forEach(item -> {

			MongoIterable<String> collectionNames = this.client.getDatabase(item.getString("name")).listCollectionNames();
			list.add(MongodbDataBase.builder()
					.name(item.get("name").toString())
					.collections(Lists.newArrayList(collectionNames))
					.disk(HexTransformationUtils.formatSize(item.getDouble("sizeOnDisk").longValue()))
					.build());
		});

		return list;
	}

	public List<String> getAllNamespaces() {
		Document document = this.client.getDatabase("admin").runCommand(new BasicDBObject("top", 1));
		Map<String, Object> map = (Map<String, Object>) document.get("totals");
		ArrayList<String> arrayList = Lists.newArrayList();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().contains("note") || entry.getKey().contains("ok")) {
				continue;
			}
			arrayList.add(entry.getKey());
		}
		return arrayList;
	}

	public MongodbOSInfo getOsInfo() {
		Document document = this.client.getDatabase("admin")
				.runCommand(new BasicDBObject("hostInfo", 1));
		Document system = (Document) document.get("system");
		Document os = (Document) document.get("os");
		return MongodbOSInfo.builder()
				.name(os.getString("name"))
				.version(os.getString("version"))
				.cpuCoreSize(system.getInteger("numCores"))
				.memorySize(system.getInteger("memSizeMB").longValue())
				.build();
	}

	public String getMongodbLockInfo() {
		Document document = this.client.getDatabase("admin").runCommand(new BasicDBObject("lockInfo", 1));
		return document.toJson();
	}

	public String getMongodbReplicationStatusInfo() {
		Document document = this.client.getDatabase("admin").runCommand(new BasicDBObject("replSetGetStatus", 1));
		return document.toJson();
	}

	public String getMongodbReplicationConfig() {
		Document document = this.client.getDatabase("admin").runCommand(new BasicDBObject("replSetGetConfig", 1));
		return document.toJson();
	}

	public String getCollectionSystemInfo(String dataBase, String collectionName) {
		Document document = this.client.getDatabase(dataBase).runCommand(new Document("collStats", collectionName));
		return document.toJson();
	}




}

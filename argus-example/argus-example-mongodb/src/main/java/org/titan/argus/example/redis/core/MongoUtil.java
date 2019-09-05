package org.titan.argus.example.redis.core;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

/**
 * @author starboyate
 */
public class MongoUtil {
	private static final String URI_PREFIX = "mongodb://";
	public static MongoClient create(String host, Integer port, String userName, String password) {
		String uri = null;
		if (StringUtils.isBlank(userName) && StringUtils.isBlank(password)) {
			uri = URI_PREFIX + host + ":" + port;
		} else {
			uri = URI_PREFIX + userName + ":" + password + "@" + host + ":" + port;
		}
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);
		return mongoClient;
	}

	public static MongoClient create(String host, Integer port) {
		return create(host, port, null, null);
	}

	public static void main(String[] args) {
		MongoClient mongoClient = create("192.168.1.235", 27017);
		Document document = mongoClient.getDatabase("admin").runCommand(new BasicDBObject("serverStatus", 1));
//		Document document1 = mongoClient.getDatabase("admin").runCommand(new BasicDBObject("stat", 1));

		Document document2 = mongoClient.getDatabase("admin").runCommand(new BasicDBObject("features", 1));
	}

}

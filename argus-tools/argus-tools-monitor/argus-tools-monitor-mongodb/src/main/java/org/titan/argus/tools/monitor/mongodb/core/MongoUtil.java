package org.titan.argus.tools.monitor.mongodb.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.commons.lang3.StringUtils;

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


}

package org.titan.argus.client.mongodb;


import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;


/**
 * @author starboyate
 */
public class MongodbRepository {

	private final MongoProperties properties;

	private static final String MONGODB_URI_PREFIX = "mongodb://";


	public MongodbRepository(MongoProperties properties) {
		this.properties = properties;
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
			host = uri.substring(MONGODB_URI_PREFIX.length(), uri.lastIndexOf(":"));
		}
		if (port == null) {
			port = Integer.valueOf(uri.substring(uri.lastIndexOf(":") + 1, uri.lastIndexOf("/")));
		}
		return MongodbNode.builder().host(host).port(port).userName(userName).password(password).build();
	}





}

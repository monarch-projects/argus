package org.titan.argus.client.mongodb;


import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.titan.argus.plugin.mongodb.entities.MongodbNode;


/**
 * @author starboyate
 */
public class MongodbRepository {

	private final MongoProperties properties;


	public MongodbRepository(MongoProperties properties) {
		this.properties = properties;
	}

	public MongodbNode getNode() {
		String password = null;
		if (null != this.properties.getPassword()) {
			password = new String(this.properties.getPassword());
		}
		return MongodbNode.builder()
				.host(this.properties.getHost())
				.port(this.properties.getPort())
				.userName(this.properties.getUsername())
				.password(password)
				.build();
	}





}

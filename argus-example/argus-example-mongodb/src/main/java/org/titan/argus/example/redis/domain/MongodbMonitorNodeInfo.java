package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbMonitorNodeInfo {
	private String id;

	private String host;

	private String version;

	private Long uptime;

	private Long connections;

	/**
	 * 在mongos中，操作被重做的次数
	 */
	private Boolean writeBacksQueued;


}

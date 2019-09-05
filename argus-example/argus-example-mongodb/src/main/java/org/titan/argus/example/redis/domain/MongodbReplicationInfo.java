package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbReplicationInfo {
	/**
	 * replSet结构定义的名称
	 */
	private String setName;

	/**
	 * 当前实例是否是replSet结构中的Master节点
	 */
	private Boolean isMaster;

	/**
	 * 当前实例是否是replSet结构中的secondary节点
	 */
	private Boolean secondary;

	/**
	 * replSet结构中每个节点情况
	 */
	private String hosts;

}

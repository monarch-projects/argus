package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbAssertsInfo {
	/**
	 * 自实例启动以来,断言正常的次数
	 */
	private Integer regular;

	/**
	 * 自实例启动以来,断言警告的次数
	 */
	private Integer warn;

	/**
	 * 自实例启动以来，断言内部错误的次数
	 */
	private Integer msg;

	/**
	 * 自实例启动以来,因用户使用造成的错误而被断言次数
	 */
	private Integer user;

	/**
	 * 断言被翻转的次数
	 */
	private Integer rollovers;
}

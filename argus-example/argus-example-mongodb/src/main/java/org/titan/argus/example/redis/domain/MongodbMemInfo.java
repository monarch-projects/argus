package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbMemInfo {
	/**
	 * 目前为止总共使用的物理内存,单位是MB
	 */
	private Long resident;

	/**
	 * 当前Mongodb实例使用的虚拟内存大小，单位MB，
	 * 一般情况下比mem.map的值要超大一点，
	 * 如果大很多很有可能发生内存泄露，
	 * 如果使用journal,大约是2倍的map值
	 */
	private Long virtual;

	/**
	 * Mongodb使所有数据都映射到内存中，所以这个值可以看似整个数据量的值
	 */
	private Long mapped;

	/**
	 * 机器位数
	 */
	private Integer bits;

	/**
	 * 本机是否支持内存扩展
	 */
	private Boolean supported;
}

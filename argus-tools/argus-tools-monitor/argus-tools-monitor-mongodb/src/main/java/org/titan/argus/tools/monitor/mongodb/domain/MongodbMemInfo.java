package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * mongdb内存信息指标
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}

package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * mongdb网络信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbNetworkInfo {
	/**
	 * 数据库网络流量的接收量的字节数
	 */
	private Long bytesIn;

	/**
	 * 数据库发送的网络流量的字节数
	 */
	private Long bytesOut;

	/**
	 * 服务器已收到的不同请求的总数
	 */
	private Long numRequests;
}

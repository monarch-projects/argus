package org.titan.argus.client.mongodb;

import lombok.*;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongodbNode {
	private String host;

	private Integer port;

	private String userName;

	private String password;
}

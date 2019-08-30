package org.titan.argus.plugin.mongodb.entities;

import lombok.*;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MongodbNode {
	private String host;

	private Integer port;

	private String userName;

	private String password;

	private String version;
}

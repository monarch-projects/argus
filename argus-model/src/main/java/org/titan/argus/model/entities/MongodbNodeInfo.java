package org.titan.argus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MongodbNodeInfo {
	private String id;

	private String host;

	private Integer port;

	private String userName;

	private String password;
}

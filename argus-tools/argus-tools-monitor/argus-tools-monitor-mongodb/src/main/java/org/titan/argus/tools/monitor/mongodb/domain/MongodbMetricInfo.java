package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongodbMetricInfo {
	private MongodbMemInfo mongodbMemInfo;

	private MongodbOpcountersInfo mongodbOpcountersInfo;

	private MongodbClientConnectionsInfo mongodbClientConnectionsInfo;

	private MongodbNetworkInfo mongodbNetworkInfo;
}

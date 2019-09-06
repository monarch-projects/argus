package org.titan.argus.example.redis.core;

import org.apache.commons.lang3.Validate;
import org.titan.argus.example.redis.domain.*;


/**
 * @author starboyate
 */
public class MongodbInfoConvert {
	public static MongodbMonitorNodeInfo convertToMongodbMonitorNodeInfo(MongodbClientConnectionsInfo mongodbClientConnectionsInfo,
			MongodbNetworkInfo mongodbNetworkInfo,
			MongodbOpcountersInfo mongodbOpcountersInfo,
			MongodbMemInfo mongodbMemInfo) {
		Validate.notNull(mongodbClientConnectionsInfo);
		Validate.notNull(mongodbNetworkInfo);
		Validate.notNull(mongodbOpcountersInfo);
		Validate.notNull(mongodbMemInfo);
		return MongodbMonitorNodeInfo.builder()
				.clientConnectionsCurrent(mongodbClientConnectionsInfo.getCurrent())
				.clientConnectionsActive(mongodbClientConnectionsInfo.getActive())
				.clientConnectionsActiveClientsReaders(mongodbClientConnectionsInfo.getActiveClientsReaders())
				.clientConnectionsActiveClientsWriters(mongodbClientConnectionsInfo.getActiveClientsWriters())
				.clientConnectionsAvailable(mongodbClientConnectionsInfo.getAvailable())
				.clientConnectionsTotalCreated(mongodbClientConnectionsInfo.getTotalCreated())
				.networkBytesIn(mongodbNetworkInfo.getBytesIn())
				.networkBytesOut(mongodbNetworkInfo.getBytesOut())
				.networkNumRequests(mongodbNetworkInfo.getNumRequests())
				.opcountersCommand(mongodbOpcountersInfo.getCommand())
				.opcountersDelete(mongodbOpcountersInfo.getDelete())
				.opcountersGetMore(mongodbOpcountersInfo.getGetMore())
				.opcountersInsert(mongodbOpcountersInfo.getInsert())
				.opcountersQuery(mongodbOpcountersInfo.getQuery())
				.opcountersUpdate(mongodbOpcountersInfo.getUpdate())
				.memResident(mongodbMemInfo.getResident())
				.memVirtual(mongodbMemInfo.getVirtual())
				.build();

	}

	public static MongodbMemInfo convertToMemInfo(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
		return MongodbMemInfo.builder()
				.resident(mongodbMonitorNodeInfo.getMemResident())
				.virtual(mongodbMonitorNodeInfo.getMemVirtual())
				.build();
	}

	public static MongodbClientConnectionsInfo convertToClientConnection(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
		return MongodbClientConnectionsInfo.builder()
				.active(mongodbMonitorNodeInfo.getClientConnectionsActive())
				.activeClientsReaders(mongodbMonitorNodeInfo.getClientConnectionsActiveClientsReaders())
				.activeClientsWriters(mongodbMonitorNodeInfo.getClientConnectionsActiveClientsWriters())
				.current(mongodbMonitorNodeInfo.getClientConnectionsCurrent())
				.totalCreated(mongodbMonitorNodeInfo.getClientConnectionsTotalCreated())
				.available(mongodbMonitorNodeInfo.getClientConnectionsAvailable())
				.build();
	}

	public static MongodbOpcountersInfo convertToOpcounters(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
		return MongodbOpcountersInfo.builder()
				.command(mongodbMonitorNodeInfo.getOpcountersCommand())
				.delete(mongodbMonitorNodeInfo.getOpcountersDelete())
				.getMore(mongodbMonitorNodeInfo.getOpcountersGetMore())
				.insert(mongodbMonitorNodeInfo.getOpcountersInsert())
				.query(mongodbMonitorNodeInfo.getOpcountersQuery())
				.update(mongodbMonitorNodeInfo.getOpcountersUpdate())
				.build();
	}

	public static MongodbNetworkInfo convertToNetwork(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
		return MongodbNetworkInfo.builder()
				.numRequests(mongodbMonitorNodeInfo.getNetworkNumRequests())
				.bytesOut(mongodbMonitorNodeInfo.getNetworkBytesOut())
				.bytesIn(mongodbMonitorNodeInfo.getNetworkBytesIn())
				.build();
	}
}

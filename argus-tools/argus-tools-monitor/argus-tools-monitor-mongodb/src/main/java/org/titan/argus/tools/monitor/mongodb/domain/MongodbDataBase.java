package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongodbDataBase {

	private static Logger logger = LoggerFactory.getLogger(MongodbDataBase.class);


	private String name;

	private String disk;

	private List<String> collections;

	public static void main(String[] args) {
		logger.error("123");
	}

}

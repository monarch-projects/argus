package org.titan.argus.plugin.mongodb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongodbDataBase {
	private String name;

	private String disk;

	private List<String> collections;
}

package org.titan.argus.storage.es.service;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

/**
 * @author starboyate
 */
public abstract class BaseMonitorService {
	protected  RangeQueryBuilder getRangerBuilder(Long startTime, Long endTime) {
		RangeQueryBuilder builder = QueryBuilders.rangeQuery("createTime");
		if (null != startTime) {
			builder.gte(startTime);
		}
		if (null != endTime) {
			builder.lte(endTime);
		}
		if (startTime != null && endTime != null && endTime.compareTo(startTime) < 0) {
			throw new IndexOutOfBoundsException("endTime must be more than the startTime");
		}
		return builder;
	}
}

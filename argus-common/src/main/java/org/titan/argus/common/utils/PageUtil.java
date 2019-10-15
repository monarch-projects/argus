package org.titan.argus.common.utils;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author starboyate
 */
public final class PageUtil {
	public static<T> List<T> page(int page, int size, Collection<T> collection) {
		Validate.notNull(collection, "elements must be not null");
		int total = collection.size();
		int fromIndex = page * size;
		int toIndex = (page + 1) * size;
		if (fromIndex >= total) {
			fromIndex = total;
		}
		if (toIndex >= total) {
			toIndex = total;
		}
		if (collection instanceof List) {
			return ((List<T>) collection).subList(fromIndex, toIndex);
		}
		return new ArrayList<>(collection).subList(fromIndex, toIndex);
	}
}

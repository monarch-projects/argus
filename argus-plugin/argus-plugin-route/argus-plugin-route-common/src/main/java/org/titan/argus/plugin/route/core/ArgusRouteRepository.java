package org.titan.argus.plugin.route.core;

import org.titan.argus.plugin.route.entities.ArgusRoute;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
public interface ArgusRouteRepository {
	List<ArgusRoute> getRoutes();

	ArgusRoute getRouteById(String id);

	ArgusRoute updateRoute(String id, ArgusRoute route);

	boolean deleteRouteById(String id);
}

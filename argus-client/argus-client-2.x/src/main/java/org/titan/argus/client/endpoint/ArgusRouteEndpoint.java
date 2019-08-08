package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.plugin.route.core.ArgusRefreshRouteEventHolder;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.entities.ArgusRoute;

import java.util.Map;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "route")
public class ArgusRouteEndpoint {
	@Autowired
	private ArgusRouteRepository routeRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ArgusRefreshRouteEventHolder holder;


	@GetMapping
	public Map<String, ArgusRoute> getAllRoute() {
		return this.routeRepository.getRoutes();
	}

	@PostMapping("/{id}")
	public ArgusRoute updateRoute(@PathVariable String id, @RequestBody ArgusRoute route) {
		ArgusRoute argusRoute = this.routeRepository.updateRoute(id, route);
		this.publisher.publishEvent(holder.getEvent());
		return argusRoute;
	}

	@DeleteMapping("/{id}")
	public ArgusRoute remove(@PathVariable String id) {
		this.publisher.publishEvent(holder.getEvent());
		return this.routeRepository.deleteRouteById(id);
	}
}

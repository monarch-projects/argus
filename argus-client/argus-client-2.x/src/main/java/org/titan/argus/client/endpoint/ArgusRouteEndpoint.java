package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.entities.ArgusRoute;

import java.util.List;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "route")
public class ArgusRouteEndpoint {
	@Autowired
	private ArgusRouteRepository routeRepository;

	@Autowired
	private ApplicationEventPublisher publisher;



	@GetMapping
	public List<ArgusRoute> getAllRoute() {
		return this.routeRepository.getRoutes();
	}

	@PostMapping("/{id}")
	public ArgusRoute updateRoute(@PathVariable String id, @RequestBody ArgusRoute route) {
		ArgusRoute argusRoute = this.routeRepository.updateRoute(id, route);
		return argusRoute;
	}

	@DeleteMapping("/{id}")
	public boolean remove(@PathVariable String id) {
		return this.routeRepository.deleteRouteById(id);
	}
}

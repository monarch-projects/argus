package org.titan.argus.plugin.route.gateway.repository;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.entities.ArgusRoute;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class ArgusGatewayRouteRepository implements ArgusRouteRepository {
	@Autowired
	private RouteDefinitionLocator locator;

	@Autowired
	private RouteDefinitionWriter writer;

	private ConcurrentHashMap<String, ArgusRoute> routeCache;

	@PostConstruct
	public void init() {
		List<ArgusRoute> collect = locator.getRouteDefinitions().toStream().map(item -> convertToArgusRoute(item))
				.collect(Collectors.toList());
		this.routeCache = new ConcurrentHashMap<>(collect.size());
		collect.forEach(item -> this.routeCache.put(item.getId(), item));
	}

	@Override
	public Map<String, ArgusRoute> getRoutes() {
		return routeCache;
	}

	@Override
	public ArgusRoute getRouteById(String id) {
		return this.routeCache.get(id);
	}

	@Override
	public ArgusRoute updateRoute(@NonNull String id, @NonNull ArgusRoute route) {
		try {
			RouteDefinition routeDefinition = this.locator.getRouteDefinitions()
					.toStream()
					.filter(item -> item.getId().equals(id))
					.findFirst()
					.orElse(null);
			this.writer.delete(Mono.just(routeDefinition.getId()));
			this.writer.save(Mono.just(updateRouteDefinition(routeDefinition, route))).subscribe();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.routeCache.remove(id);
		this.routeCache.put(route.getId(), route);
		return route;
	}

	private RouteDefinition updateRouteDefinition(@NonNull RouteDefinition definition, @NonNull ArgusRoute route)
			throws Exception {
		definition.setId(route.getId());
		definition.setUri(new URI(route.getUri()));
		definition.getPredicates().stream()
				.filter(item -> item.getName().equalsIgnoreCase("path"))
				.map(PredicateDefinition::getArgs)
				.findFirst()
				.orElse(null)
				.forEach((k, v) -> {
					if (v.contains("/")) {
						v = route.getPath();
					}
				});
		Map<String, String> stringStringMap = definition.getFilters().stream()
				.filter(item -> item.getName().equalsIgnoreCase("stripprefix")).map(FilterDefinition::getArgs)
				.findFirst().orElse(null);
		stringStringMap.put("_genkey_0", route.getStripPrefix());
		return definition;
	}

	@Override
	public ArgusRoute deleteRouteById(String id) {
		this.writer.delete(Mono.just(id));
		ArgusRoute temp = this.routeCache.get(id);
		this.routeCache.remove(id);
		return temp;
	}

	private ArgusRoute convertToArgusRoute(RouteDefinition definition) {
		String stripPrefix = null;
		String path = null;
		for (PredicateDefinition predicateDefinition : definition.getPredicates()) {
			if (predicateDefinition.getName().equalsIgnoreCase("path")) {
				Map<String, String> args = predicateDefinition.getArgs();
				for (Map.Entry<String, String> entry : args.entrySet()) {
					if (!StringUtils.isBlank(entry.getValue()) && entry.getValue().contains("/")) {
						path = entry.getValue();
						break;
					}
				}
			}
		}
		for (FilterDefinition filterDefinition : definition.getFilters()) {
			if (filterDefinition.getName().equalsIgnoreCase("stripprefix")) {
				stripPrefix = filterDefinition.getArgs().get("_genkey_0");
			}
		}
		return ArgusRoute.builder()
				.id(definition.getId())
				.path(path)
				.uri(definition.getUri().toString())
				.stripPrefix(stripPrefix)
				.build();

	}
}

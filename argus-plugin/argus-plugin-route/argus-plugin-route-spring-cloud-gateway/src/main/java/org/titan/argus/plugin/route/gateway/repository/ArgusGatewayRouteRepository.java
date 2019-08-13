package org.titan.argus.plugin.route.gateway.repository;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.entities.ArgusRoute;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class ArgusGatewayRouteRepository implements ArgusRouteRepository{
	@Autowired
	private RouteDefinitionLocator locator;

	@Autowired
	private RouteDefinitionWriter writer;

	@Autowired
	private ApplicationEventPublisher publisher;



	private final String routePrefix = "CompositeDiscoveryClient_";


	@Override
	public List<ArgusRoute> getRoutes() {
		List<ArgusRoute> list = new ArrayList<>();
		locator.getRouteDefinitions().filter(item -> !item.getId().startsWith(routePrefix)).map(item -> convertToArgusRoute(item)).subscribe(item -> list.add(item));
		return list;
	}

	@Override
	public ArgusRoute getRouteById(String id) {
		List<ArgusRoute> list = new ArrayList<>(1);
		locator.getRouteDefinitions().filter(definition -> definition.getId().equals(id) && !definition.getId().startsWith(routePrefix)).map(item -> convertToArgusRoute(item)).subscribe(item -> list.add(item));
		return list.get(0);
	}

	@Override
	public ArgusRoute updateRoute(@NonNull String id, @NonNull ArgusRoute route) {
		try {
			List<RouteDefinition> list = new ArrayList<>(1);
			this.locator.getRouteDefinitions()
						.filter(item -> item.getId().equals(id))
						.subscribe(d -> list.add(d));
			RouteDefinition routeDefinition = list.get(0);
			this.writer.save(Mono.just(updateRouteDefinition(routeDefinition, route)));
			this.publisher.publishEvent(new RefreshRoutesEvent(this));
			return route;
		} catch (Exception ex) {
			throw new IllegalArgumentException("update route error: " + ex.getMessage());
		}
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
	public boolean deleteRouteById(String id) {
		Disposable subscribe = this.writer.delete(Mono.just(id)).subscribe();
		this.publisher.publishEvent(new RefreshRoutesEvent(this));
		return subscribe.isDisposed();
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

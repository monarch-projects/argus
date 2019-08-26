package org.argus.example.eureka.client;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.reactive.WebFluxEndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TestController {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private ObjectProvider<MappingDescriptionProvider> descriptionProviders;

	@Autowired
	private WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping;


	@GetMapping("/mappings")
	public ApplicationMappings test() {
		Collection<MappingDescriptionProvider> collect = descriptionProviders.orderedStream().collect(Collectors.toList());
		ApplicationContext target = this.context;
		Map<String, TestController.ContextMappings> contextMappings = new HashMap<>();
		while (target != null) {
			contextMappings.put(target.getId(), mappingsForContext(target));
			target = target.getParent();
		}
		ApplicationMappings mappings = new ApplicationMappings(contextMappings);
		Collection<ExposableWebEndpoint> endpoints = webMvcEndpointHandlerMapping.getEndpoints();
		for (ExposableWebEndpoint webEndpoint : endpoints) {
			System.out.println(webEndpoint.getRootPath());
			System.out.println(webEndpoint.getEndpointId());
		}
		return mappings;

	}

	private TestController.ContextMappings mappingsForContext(ApplicationContext applicationContext) {
		Map<String, Object> mappings = new HashMap<>();
		this.descriptionProviders.forEach(
				(provider) -> mappings.put(provider.getMappingName(), provider.describeMappings(applicationContext)));
		return new TestController.ContextMappings(mappings,
				(applicationContext.getParent() != null) ? applicationContext.getId() : null);
	}

	/**
	 * A description of an application's request mappings. Primarily intended for
	 * serialization to JSON.
	 */
	public static final class ApplicationMappings {

		private final Map<String, TestController.ContextMappings> contextMappings;

		private ApplicationMappings(Map<String, TestController.ContextMappings> contextMappings) {
			this.contextMappings = contextMappings;
		}

		public Map<String, TestController.ContextMappings> getContexts() {
			return this.contextMappings;
		}

	}

	/**
	 * A description of an application context's request mappings. Primarily intended for
	 * serialization to JSON.
	 */
	public static final class ContextMappings {

		private final Map<String, Object> mappings;

		private final String parentId;

		private ContextMappings(Map<String, Object> mappings, String parentId) {
			this.mappings = mappings;
			this.parentId = parentId;
		}

		public String getParentId() {
			return this.parentId;
		}

		public Map<String, Object> getMappings() {
			return this.mappings;
		}

	}
}

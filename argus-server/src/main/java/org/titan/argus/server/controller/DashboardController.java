package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.vo.InstanceAnalysisVO;
import org.titan.argus.model.vo.ViewsAnalysisVO;
import org.titan.argus.util.ArgusVisitsUtil;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.InstanceService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController extends BaseController {

	@Autowired
	private InstanceService instanceService;

	@GetMapping("/instances")
	public ObjectDataResponse getAllInstances() {
		Set<ArgusInstance> all = instanceService.findAll();
		List<ArgusInstance> collect = all.stream()
				.filter(item -> item.getStatus().equalsIgnoreCase("up")).collect(Collectors.toList());
		Integer availability = collect.size() / all.size();
		return new ObjectDataResponse<>(InstanceAnalysisVO.builder().size(all.size()).availability(availability).build());
	}

	@GetMapping("/views")
	public ObjectDataResponse getAllVisits() {
		return new ObjectDataResponse<>(ViewsAnalysisVO.builder().visits(ArgusVisitsUtil.get()).build());
	}
}

package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.titan.argus.model.entities.Dept;
import org.titan.argus.model.vo.DeptVO;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.DeptService;

import java.util.List;

/**
 * @author starboyate
 */
@Controller
@RequestMapping("/api/v1/dept")
public class DeptController {
	@Autowired
	private DeptService deptService;

	@GetMapping("/tree")
	public ObjectDataResponse<List<DeptVO>> getDeptTree() {
		List<Dept> allDepts = deptService.findAllDepts();

	}
}

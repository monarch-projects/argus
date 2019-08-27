package org.titan.argus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.discovery.common.entities.ArgusServiceApp;
import org.titan.argus.discovery.common.repository.AppRepository;
import org.titan.argus.service.AppService;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
@Service
public class AppServiceImpl implements AppService {
	@Autowired
	private AppRepository repository;

	@Override
	public List<ArgusServiceApp> findAll() {
		return repository.findAll();
	}
}

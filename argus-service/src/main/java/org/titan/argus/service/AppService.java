package org.titan.argus.service;

import org.titan.argus.discovery.common.entities.ArgusServiceApp;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
public interface AppService {
	List<ArgusServiceApp> findAll();
}

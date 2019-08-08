package org.titan.argus.discovery.common.repository;

import org.titan.argus.discovery.common.entities.ArgusServiceApp;

import java.util.Map;

public interface AppRepository {
	Map<String, ArgusServiceApp> findAll();
}

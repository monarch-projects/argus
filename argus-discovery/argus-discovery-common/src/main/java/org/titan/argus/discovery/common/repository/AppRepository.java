package org.titan.argus.discovery.common.repository;

import org.titan.argus.discovery.common.entities.ArgusServiceApp;

import java.util.List;

public interface AppRepository {
	List<ArgusServiceApp> findAll();

}

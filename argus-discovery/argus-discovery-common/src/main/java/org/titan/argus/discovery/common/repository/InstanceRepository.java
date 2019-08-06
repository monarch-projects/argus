package org.titan.argus.discovery.common.repository;

import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.discovery.common.event.InstanceEventManager;

import java.util.List;
import java.util.Map;

public abstract class InstanceRepository implements InstanceEventManager {
	protected abstract void update();
	public abstract Map<String, List<ArgusInstance>> findAll();

	public abstract List<ArgusInstance> getInstanceByAppName(String appName);

	@Override
	public void notifyEvent() {
		update();
	}
}

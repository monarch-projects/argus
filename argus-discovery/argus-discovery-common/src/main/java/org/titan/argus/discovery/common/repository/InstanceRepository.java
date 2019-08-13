package org.titan.argus.discovery.common.repository;

import org.titan.argus.discovery.common.disruptor.event.InstanceEventNotify;
import org.titan.argus.discovery.common.entities.ArgusInstance;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
public abstract class InstanceRepository implements InstanceEventNotify {
	public abstract void update();
	public abstract Map<String, List<ArgusInstance>> findAll();
	public abstract List<ArgusInstance> getInstanceByAppName(String appName);
	public abstract void init();

	@Override
	public void register() {
		init();
	}

	@Override
	public void statusChange() {
		update();
	}
}

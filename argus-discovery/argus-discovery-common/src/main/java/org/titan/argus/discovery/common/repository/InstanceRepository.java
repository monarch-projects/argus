package org.titan.argus.discovery.common.repository;

import org.titan.argus.discovery.common.disruptor.event.InstanceEventNotify;
import org.titan.argus.discovery.common.entities.ArgusInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
public abstract class InstanceRepository implements InstanceEventNotify {
	public abstract void update();
	public abstract List<ArgusInstance> getInstanceByAppName(String appName);
	public abstract ArgusInstance getInstanceById(String id);
	public abstract Set<ArgusInstance> findAll();
	public abstract List<ArgusInstance> page(int fromIndex, int toIndex);
	public abstract void addRegisterCenter(String serviceUrl);
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

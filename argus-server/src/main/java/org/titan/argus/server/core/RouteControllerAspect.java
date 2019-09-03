package org.titan.argus.server.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.titan.argus.model.entities.InstanceMetadata;

import java.lang.reflect.Field;

/**
 * @author starboyate
 */
@Aspect
public class RouteControllerAspect {
	private final InstanceMetadataHolder holder;
	public RouteControllerAspect(InstanceMetadataHolder holder) {
		this.holder = holder;
	}
	@Pointcut("execution(public * org.titan.argus.server.controller.RouteController.*(..))")
	public void before(){}

	@Before("before()")
	public void doBefore(JoinPoint joinPoint) throws Exception {
		InstanceMetadata metadata =  this.holder.getAllInstanceMetadata().stream().filter(InstanceMetadata::getIsGateway).findFirst().orElse(null);
		Field field = joinPoint.getTarget().getClass().getDeclaredField("metadata");
		field.setAccessible(true);
		field.set(field, metadata);
	}
}

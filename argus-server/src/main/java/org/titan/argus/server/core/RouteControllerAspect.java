package org.titan.argus.server.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.server.controller.RedisController;
import org.titan.argus.server.controller.RouteController;
import org.titan.argus.service.exception.BusinessException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author starboyate
 */
@Component
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
		if (null == metadata) {
			throw new BusinessException("gateway not found");
		}
		Field field = joinPoint.getTarget().getClass().getDeclaredField("metadata");
		field.setAccessible(true);
		field.set(joinPoint.getTarget(), metadata);
	}
}

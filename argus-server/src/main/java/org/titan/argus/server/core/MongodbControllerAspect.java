package org.titan.argus.server.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.titan.argus.model.entities.InstanceMetadata;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@Component
@Aspect
public class MongodbControllerAspect {
	private final InstanceMetadataHolder holder;
	public MongodbControllerAspect(InstanceMetadataHolder holder) {
		this.holder = holder;
	}
	@Pointcut("execution(public * org.titan.argus.server.controller.MongoController.*(..))")
	public void before(){}

	@Before("before()")
	public void doBefore(JoinPoint joinPoint) throws Exception {
		Set<InstanceMetadata> collect =  this.holder.getAllInstanceMetadata().stream().filter(InstanceMetadata::getIsUsedMongodb)
				.collect(Collectors.toSet());
		Field field = joinPoint.getTarget().getClass().getDeclaredField("metadataSet");
		field.setAccessible(true);
		field.set(joinPoint.getTarget(), collect);
	}
}

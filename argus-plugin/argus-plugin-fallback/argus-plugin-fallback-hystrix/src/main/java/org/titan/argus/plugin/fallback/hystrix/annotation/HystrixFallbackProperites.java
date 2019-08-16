package org.titan.argus.plugin.fallback.hystrix.annotation;

import java.lang.annotation.*;

/**
 * @author starboyate
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HystrixFallbackProperites {
}

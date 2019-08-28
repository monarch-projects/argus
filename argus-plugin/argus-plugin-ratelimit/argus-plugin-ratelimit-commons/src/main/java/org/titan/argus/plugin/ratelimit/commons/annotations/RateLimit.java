package org.titan.argus.plugin.ratelimit.commons.annotations;

import java.lang.annotation.*;

/**
 * @Title: RateLimit
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RateLimit {
    int limit() default 10;

    long waitTime() default 500;
}

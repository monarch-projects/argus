package org.titan.argus.client.annotation;


import java.lang.annotation.*;

/**
 * @author starboyate
 * Used to mark the test tool，Do not use in formal
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestTool {
}

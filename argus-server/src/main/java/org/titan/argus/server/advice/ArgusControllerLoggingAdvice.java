package org.titan.argus.server.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Title: ArgusControllerLoggingAdvice
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Aspect
@Component
@Slf4j
public class ArgusControllerLoggingAdvice {

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] objects = point.getArgs();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        StringBuilder builder = new StringBuilder();
        for (Object o : objects) {
            if (Objects.isNull(o)) {
                builder.append("null,");
                continue;
            }
            builder.append(o.toString()).append(",");
        }

        log.info("方法 {}.{} 被调用，入参:{}", signature.getDeclaringTypeName(), method.getName(), builder.toString());

        Object result = point.proceed();

        log.info("方法 {}.{} 被调用,返回值:{}", signature.getDeclaringTypeName(), method.getName(), result.toString());

        return result;
    }

}

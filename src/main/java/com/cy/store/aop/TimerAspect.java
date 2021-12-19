package com.cy.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component // 将当前类的对象创建使用、维护交给Spring容器维护
@Aspect // 将当前类标记为切面类
public class TimerAspect {

    // 定义切面方法
    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 先记录当前时间
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        // 记录目标方法执行后的时间
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start));
        return result;
    }
}

package com.spring.project.portfolio;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
//https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/html/aop.html

@Aspect
@Component
public class LogAspect {

	private final Logger logger = LogManager.getLogger(this.getClass());

	// @Pointcut("execution(public String com..*Controller.* (..))")
	// @Pointcut("@within(org.springframework.stereotype.Controller)")
	@Pointcut("execution(public String * (..)) && "
			+ "@within(org.springframework.stereotype.Controller)")
	public void pcut() {
	}

	@Around("pcut()")
	public Object aroundShow(ProceedingJoinPoint pjp) throws Throwable {
		String packageName = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		long start = System.currentTimeMillis();

		// logger.info("Entering method [" + packageName + "." + methodName + "]");
		Object r = pjp.proceed();

		String username = "NotAvailable";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			username = authentication.getName();
		}
		
		long elapsedTime = System.currentTimeMillis() - start;
		logger.info("[" + packageName + "-" + methodName + "()] has been executed by " + username + " in " + elapsedTime
				+ " (ms)");
		return r;
	}

}
package com.w2m.tvmedia.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.w2m.tvmedia.common.base.W2MEntity;
import com.w2m.tvmedia.common.exception.W2MNotFoundException;

@Aspect
@Component
public class LoggingAspect {

	Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("@annotation(W2MLog)")
	public void logPointcut() {
	}

	@Around("logPointcut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Long arg = (Long) joinPoint.getArgs()[0];
		if (arg != null && arg.longValue() < 0) {
			log.error("El id de la nave no debe de ser negativo.");
			throw new W2MNotFoundException(W2MEntity.SPACESHIP, arg.longValue());
		}

		return joinPoint.proceed();
	}

}

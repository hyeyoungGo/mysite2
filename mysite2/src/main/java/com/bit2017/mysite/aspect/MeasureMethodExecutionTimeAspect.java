package com.bit2017.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureMethodExecutionTimeAspect {
	
	@Around("execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		// before advice
		StopWatch sw = new StopWatch();
		sw.start();
		
		// dao method 실행
		Object result = pjp.proceed();
		
		// after advice
		sw.stop();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;
		
		System.out.println("[ExecutionTime][" + taskName + "]" + sw.getTotalTimeMillis() + "mils.");
		
		return result;
	}

}

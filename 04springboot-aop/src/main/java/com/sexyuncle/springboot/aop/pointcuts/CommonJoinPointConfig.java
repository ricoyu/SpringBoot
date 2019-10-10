package com.sexyuncle.springboot.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("execution(* com.sexyuncle.springboot.aop.service.*.*(..))")
	public void businessLayerExecution() {
	}
	
	@Pointcut("@annotation(com.sexyuncle.springboot.aop.annotation.TrackTime)")
	public void businessLayerExecutionAnnotation() {
	}
}
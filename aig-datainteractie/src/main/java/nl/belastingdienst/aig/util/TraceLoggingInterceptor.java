package nl.belastingdienst.aig.util;

import java.lang.reflect.Method;

/**
 * Spring AOP interceptor voor trace logging.
 * 
 * @author vriej39
 */
public class TraceLoggingInterceptor { // implements MethodBeforeAdvice, AfterReturningAdvice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method,
	 * java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target)
			throws Throwable {
		TraceLoggingUtil.exit(target, method.getName(), method.getReturnType()
			.equals(Void.TYPE), returnValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(final Method method, final Object[] args, final Object target) throws Throwable {
		TraceLoggingUtil.enter(target, method.getName(), args);
	}

}

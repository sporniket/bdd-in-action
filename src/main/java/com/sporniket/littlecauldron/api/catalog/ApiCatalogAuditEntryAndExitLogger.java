package com.sporniket.littlecauldron.api.catalog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import com.sporniket.littlecauldron.aspect.AuditEntryAndExitLogger;;

/**
 * Effective aspect for tracing the entry and the exit of a restcall of this application.
 */
@Aspect
@Configuration
public class ApiCatalogAuditEntryAndExitLogger extends AuditEntryAndExitLogger
{

	@Around("(execution(* com.sporniket.littlecauldron..*.*(..))) && isToTrace()")
	public Object traceMethod(ProceedingJoinPoint pjp) throws Throwable
	{
		return doTraceMethod(pjp);
	}

}

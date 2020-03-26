/**
 * 
 */
package com.sporniket.littlecauldron.filter;

import static java.lang.String.format;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;

import org.slf4j.MDC;

public abstract class RequestIdInterceptor
{
	private final String NAME_IN_LOG_CONTEXT = "request_id";

	private final String NAME_IN_REQUEST = "X-Request-Id";

	public void extractRequestIdOrGenerate(ContainerRequestContext requestContext, String prefix) throws IOException
	{
		final String requestIdInRequestHeader = requestContext.getHeaderString(NAME_IN_REQUEST);
		String requestIdTrimmedToEmpty = (null == requestIdInRequestHeader) ? "" : requestIdInRequestHeader.trim();
		final String correlationId = (!requestIdTrimmedToEmpty.isEmpty()) //
				? requestIdTrimmedToEmpty //
				: format("%s-%s", prefix, UUID.randomUUID().toString());
		MDC.put(NAME_IN_LOG_CONTEXT, correlationId);
	}

	public void clearMdc() throws IOException
	{
		MDC.remove(NAME_IN_LOG_CONTEXT);
	}
}

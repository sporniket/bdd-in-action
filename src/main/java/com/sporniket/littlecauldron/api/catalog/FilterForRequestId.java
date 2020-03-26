package com.sporniket.littlecauldron.api.catalog;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.sporniket.littlecauldron.filter.RequestIdInterceptor;

@Provider
public class FilterForRequestId extends RequestIdInterceptor implements ContainerRequestFilter, ContainerResponseFilter
{

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
	{
		clearMdc();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		extractRequestIdOrGenerate(requestContext, "catalog");
	}

}

package com.sporniket.littlecauldron.api.catalog;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Normalize the error response.
 */
@Provider
public class HandlerForThrowables implements ExceptionMapper<Throwable>
{

	@Override
	public Response toResponse(Throwable exception)
	{
		ResourceError error = new ResourceError();
		error.setError("-1");
		error.setReason(exception.getMessage());
		return Response//
				.status(INTERNAL_SERVER_ERROR)//
				.type(APPLICATION_JSON)//
				.entity(error)//
				.build();
	}

}

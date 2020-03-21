package com.sporniket.littlecauldron.api.catalog;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Normalize the error response.
 */
@Provider
public class HandlerForApiCatalogException implements ExceptionMapper<ApiCatalogException>
{

	@Override
	public Response toResponse(ApiCatalogException exception)
	{
		ResourceError error = new ResourceError();
		error.setError(Integer.toString(exception.getCode().getValue()));
		error.setReason(exception.getMessage());
		return Response//
				.status(exception.getStatus())//
				.type(APPLICATION_JSON)//
				.entity(error)//
				.build();
	}

}

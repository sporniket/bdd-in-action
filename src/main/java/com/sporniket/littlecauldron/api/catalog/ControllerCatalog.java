package com.sporniket.littlecauldron.api.catalog;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;

import com.sporniket.littlecauldron.aspect.LogEntryAndExit;

@Controller
@Path("/catalog")
public class ControllerCatalog
{
	ResourceProduct LC_01 = createProduct("LC01", "Standard Quality");

	@GET
	@Produces(APPLICATION_JSON)
	@Path("/products/{id}")
	@LogEntryAndExit
	public ResourceProduct getProduct(@PathParam("id") String id)
	{
		if (LC_01.getId().equals(id))
		{
			return LC_01;
		}
		throw new ApiCatalogException(NOT_FOUND, ErrorCode.E1001_NOT_FOUND, String.format("Product '%s' not found.", id));
	}

	private ResourceProduct createProduct(String id, String label)
	{
		ResourceProduct result = new ResourceProduct();
		result.setId(id);
		result.setLabel(label);
		return result;
	}

}

package com.sporniket.littlecauldron.api.catalog;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sporniket.littlecauldron.api.catalog.entities.EntityProducts;
import com.sporniket.littlecauldron.aspect.LogEntryAndExit;

@Controller
@Path("/catalog")
public class ControllerCatalog
{
	@Autowired
	private ServiceCatalog serviceCatalog;

	@GET
	@Produces(APPLICATION_JSON)
	@Path("/products/{id}")
	@LogEntryAndExit
	public ResourceProduct getProduct(@PathParam("id") String id)
	{
		EntityProducts productEntity = serviceCatalog.getProduct(id);
		if (null == productEntity)
		{
			throw new ApiCatalogException(NOT_FOUND, ErrorCode.E1001_NOT_FOUND, format("Product '%s' not found.", id));
		}

		return mapFromEntityProducts(productEntity);
	}

	@GET
	@Produces(APPLICATION_JSON)
	@Path("/products/")
	@LogEntryAndExit
	public ResourceProducts getProducts()
	{
		List<EntityProducts> productEntities = serviceCatalog.getProducts();
		if (null == productEntities)
		{
			throw new ApiCatalogException(NOT_FOUND, ErrorCode.E1001_NOT_FOUND, "Products not found.");
		}

		ResourceProducts result = new ResourceProducts();
		result.setProducts(productEntities.stream()//
				.map(e -> mapFromEntityProducts(e)) //
				.collect(toList()));

		return result;
	}

	private ResourceProduct mapFromEntityProducts(EntityProducts entity)
	{
		ResourceProduct result = new ResourceProduct();
		result.setId(entity.getSku());
		result.setLabel(entity.getLabel());
		return result;
	}

}

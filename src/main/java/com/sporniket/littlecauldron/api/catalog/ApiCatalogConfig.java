package com.sporniket.littlecauldron.api.catalog;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiCatalogConfig extends ResourceConfig
{

	public ApiCatalogConfig()
	{
		super();
		register(ControllerCatalog.class);
		register(HandlerForThrowables.class);
		register(HandlerForApiCatalogException.class);
	}

}

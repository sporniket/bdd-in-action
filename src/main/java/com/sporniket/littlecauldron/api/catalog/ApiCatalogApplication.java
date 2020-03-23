package com.sporniket.littlecauldron.api.catalog;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sporniket.littlecauldron.api.catalog")
@ComponentScan("com.sporniket.littlecauldron.aspect")
public class ApiCatalogApplication // extends Application
{

	public static void main(String[] args)
	{
		run(ApiCatalogApplication.class, args);
	}

}

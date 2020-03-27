package com.sporniket.littlecauldron.api.catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sporniket.littlecauldron.api.catalog.entities.DaoProducts;
import com.sporniket.littlecauldron.api.catalog.entities.EntityProducts;

@Service
public class ServiceCatalog
{
	@Autowired
	private DaoProducts daoProducts;

	public EntityProducts getProduct(String sku)
	{
		return daoProducts.findBySku(sku);
	}

	public List<EntityProducts> getProducts()
	{
		return daoProducts.findAll();
	}
}

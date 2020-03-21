package com.sporniket.littlecauldron.api.catalog;

public class ResourceProduct
{
	/**
	 * The product code
	 */
	private String id;

	/**
	 * A short human readable label
	 */
	private String label;

	public String getId()
	{
		return id;
	}

	public String getLabel()
	{
		return label;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}
}

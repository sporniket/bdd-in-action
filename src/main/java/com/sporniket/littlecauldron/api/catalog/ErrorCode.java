package com.sporniket.littlecauldron.api.catalog;

public enum ErrorCode
{
	E1001_NOT_FOUND(1001);

	private final int value;

	public int getValue()
	{
		return value;
	}

	private ErrorCode(int value)
	{
		this.value = value;
	}

}

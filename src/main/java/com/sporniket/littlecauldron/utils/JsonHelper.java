package com.sporniket.littlecauldron.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonHelper
{

	private static final ObjectMapper DEFAULT_MAPPER;

	static
	{
		ObjectMapper mapper = new ObjectMapper();

		// Configure mapper
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setDefaultPropertyInclusion(Include.NON_NULL);
		DEFAULT_MAPPER = mapper;
	}

	public static String jsonFrom(Object object) throws IOException
	{
		return DEFAULT_MAPPER.writeValueAsString(object);
	}

	public static <T> T objectFrom(InputStream input, String charset, Class<T> returnClass)
			throws JsonParseException, JsonMappingException, IOException
	{
		return DEFAULT_MAPPER.readValue(new InputStreamReader(input, charset), returnClass);
	}

	public static <T> T objectFrom(String json, Class<T> returnClass) throws JsonParseException, JsonMappingException, IOException
	{
		return DEFAULT_MAPPER.readValue(json, returnClass);
	}
}

package com.sporniket.littlecauldron.api.catalog;

import javax.ws.rs.core.Response.Status;

public class ApiCatalogException extends RuntimeException
{
	private static final ErrorCode DEFAULT_CODE = null;

	private static final Status DEFAULT_STATUS = Status.INTERNAL_SERVER_ERROR;

	private static final long serialVersionUID = -5170357903125890033L;

	private static String getMessageFromCause(Throwable cause)
	{
		return cause == null ? null : cause.toString();
	}

	private final ErrorCode code;

	private final Status status;

	public ApiCatalogException()
	{
		this(DEFAULT_STATUS, DEFAULT_CODE);
	}

	public ApiCatalogException(ErrorCode code)
	{
		this(DEFAULT_STATUS, code);
	}

	public ApiCatalogException(ErrorCode code, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		this(DEFAULT_STATUS, code, message, cause, enableSuppression, writableStackTrace);
	}

	public ApiCatalogException(Status status, ErrorCode code)
	{
		this(status, code, null, null, true, true);
	}

	public ApiCatalogException(Status status, ErrorCode code, String message)
	{
		this(status, code, message, null, true, true);
	}

	public ApiCatalogException(Status status, ErrorCode code, String message, Throwable cause)
	{
		this(status, code, message, cause, true, true);
	}

	protected ApiCatalogException(Status status, ErrorCode code, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
		this.code = code;
	}

	public ApiCatalogException(Status status, ErrorCode code, Throwable cause)
	{
		this(status, code, getMessageFromCause(cause), cause, true, true);
	}

	public ApiCatalogException(String message)
	{
		this(message, null);
	}

	public ApiCatalogException(String message, Throwable cause)
	{
		this(message, cause, true, true);
	}

	protected ApiCatalogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		this(DEFAULT_CODE, message, cause, enableSuppression, writableStackTrace);
	}

	public ApiCatalogException(Throwable cause)
	{
		this(getMessageFromCause(cause), cause);
	}

	public ErrorCode getCode()
	{
		return code;
	}

	public Status getStatus()
	{
		return status;
	}

}

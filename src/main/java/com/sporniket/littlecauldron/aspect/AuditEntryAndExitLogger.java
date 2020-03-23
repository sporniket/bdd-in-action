package com.sporniket.littlecauldron.aspect;

import static com.sporniket.littlecauldron.utils.JsonHelper.jsonFrom;
import static java.lang.String.format;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.Path;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base aspect for tracing the entry and the exit of a restcall.
 */
@Aspect
public abstract class AuditEntryAndExitLogger
{
	// ** The all purpose logger.
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditEntryAndExitLogger.class);

	protected Object doTraceMethod(ProceedingJoinPoint pjp) throws Throwable
	{
		final String className = pjp.getTarget().getClass().getName();
		final String methodName = pjp.getSignature().getName();
		final String args = (LOGGER.isDebugEnabled()) ? retrieveArgumentsList(pjp) : "...";
		try
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug(format("INVOKING  %s.%s(%s)", className, methodName, args));
			}
			final Object result = pjp.proceed();
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug(format("RETURNING %s", jsonFrom(result)));
			}
			return result;
		}
		catch (final Throwable t)
		{
			if (LOGGER.isDebugEnabled())
			{
				final Map<String, String> error = new LinkedHashMap<>(3);
				final String methodPath = retrieveResourcePath(pjp);
				if (null != methodPath)
				{
					final Map<String, String> restCall = new LinkedHashMap<>(2);
					restCall.put("path", methodPath);
					restCall.put("args", args);
					error.put("restCall", jsonFrom(restCall));
				}
				error.put("error", t.getClass().getName());
				error.put("message", t.getLocalizedMessage());
				error.put("location", t.getStackTrace()[0].toString());
				LOGGER.debug(format("ERROR     %s", jsonFrom(error)), t);
			}
			else
			{
				LOGGER.error(format("ERROR BY  %s.%s(...)", className, methodName), t);
			}
			throw t;
		}
	}

	@Pointcut("@annotation(com.sporniket.littlecauldron.aspect.LogEntryAndExit)")
	public void isToTrace()
	{
	}

	private String retrieveArgumentsList(ProceedingJoinPoint pjp)
	{
		final StringBuilder argsBuild = new StringBuilder();
		for (final Object arg : pjp.getArgs())
		{
			if (argsBuild.length() > 0)
			{
				argsBuild.append(", ");
			}
			argsBuild.append(retrieveStringFrom(arg));
		}
		return argsBuild.toString();
	}

	/**
	 * Retourne le chemin de la ressource lorsque la méthode est annotée avec {@link Path}.
	 * 
	 * @param pjp
	 *            le point d'injection de l'aspet.
	 * @return le chemin trouvé ou <code>null</code>.
	 */
	private String retrieveResourcePath(ProceedingJoinPoint pjp)
	{
		Method called = ((MethodSignature) pjp.getSignature()).getMethod();
		Path pathAnnotation = called.getAnnotation(Path.class);
		if (null == pathAnnotation)
		{
			return null;
		}

		String methodPath = pathAnnotation.value();

		Path baseAnnotation = pjp.getTarget().getClass().getAnnotation(Path.class);
		String basePath = (null == baseAnnotation) ? "?/" : baseAnnotation.value();
		String separator = (basePath.endsWith("/") || methodPath.startsWith("/")) ? "" : "/";
		return basePath + separator + methodPath;
	}

	private String retrieveStringFrom(Object toDump)
	{
		if (null == toDump)
		{
			return "null";
		}
		else if (toDump instanceof CharSequence)
		{
			return new StringBuilder().append('"').append(toDump.toString().replace("\"", "\\\"")).append('"').toString();
		}
		else if (toDump.getClass().getName().startsWith("com.sporniket.littlecauldron."))
		{
			try
			{
				return jsonFrom(toDump);
			}
			catch (final IOException e)
			{
				return toDump.toString();
			}
		}
		else
		{
			return toDump.toString();
		}
	}

}

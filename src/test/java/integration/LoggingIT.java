package integration;

import static java.lang.String.format;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.logging.LogLevel.WARN;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.sporniket.littlecauldron.api.catalog.ApiCatalogApplication;
import com.sporniket.littlecauldron.aspect.AuditEntryAndExitLogger;

@SpringBootTest(classes = ApiCatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoggingIT
{
	private static final String EXPECTED_TRACE_RIGHT_CALL_DEBUG_ENTRY = "INVOKING  com.sporniket.littlecauldron.api.catalog.ControllerCatalog.getProduct(\"LC01\")";

	private static final String EXPECTED_TRACE_RIGHT_CALL_DEBUG_EXIT = "RETURNING {\"id\":\"LC01\",\"label\":\"Standard Quality\"}";

	private static final String EXPECTED_TRACE_WRONG_CALL_DEBUG_ENTRY = "INVOKING  com.sporniket.littlecauldron.api.catalog.ControllerCatalog.getProduct(\"WHATEVER\")";

	private static final String EXPECTED_TRACE_WRONG_CALL_DEBUG_ERROR = "ERROR     {\"restCall\":\"{\\\"path\\\":\\\"/catalog/products/{id}\\\",\\\"args\\\":\\\"\\\\\\\"WHATEVER\\\\\\\"\\\"}\",\"error\":\"com.sporniket.littlecauldron.api.catalog.ApiCatalogException\",\"message\":\"Product 'WHATEVER' not found.\",\"location\":\"com.sporniket.littlecauldron.api.catalog.ControllerCatalog.getProduct(ControllerCatalog.java";

	private static final String EXPECTED_TRACE_WRONG_CALL_ERROR = "ERROR BY  com.sporniket.littlecauldron.api.catalog.ControllerCatalog.getProduct(...)";

	private static final String FORMAT_REST_CALL_PATH = "/catalog/products/%s";

	private static final String FORMAT_REST_CALL_URL = "http://localhost:%s%s";

	private static final String TARGET_LOGGER = AuditEntryAndExitLogger.class.getName();

	private LogLevel backup;

	private LoggingSystem loggingSystem;

	@LocalServerPort
	int randomServerPort;

	private String restCallPath;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@PostConstruct
	public void finishInitialization()
	{
		restCallPath = format(FORMAT_REST_CALL_URL, randomServerPort, FORMAT_REST_CALL_PATH);
	}

	private void forceLogLevel(final LoggingSystem loggingSystem, final String targetLogger, final LogLevel logLevel)
	{
		loggingSystem.setLogLevel(targetLogger, logLevel);
	}

	private ResponseEntity<String> performCall(String idProduct)
	{
		final String effectiveCall = format(restCallPath, idProduct);
		return restTemplate.getForEntity(effectiveCall, String.class);
	}

	@AfterEach
	public void restoreErrorLogging()
	{
		forceLogLevel(loggingSystem, TARGET_LOGGER, backup);
	}

	@BeforeEach
	public void setErrorLogging()
	{
		loggingSystem = LoggingSystem.get(ClassLoader.getSystemClassLoader());
		backup = loggingSystem.getLoggerConfiguration(TARGET_LOGGER).getConfiguredLevel();
		forceLogLevel(loggingSystem, TARGET_LOGGER, LogLevel.DEBUG);
	}

	@Test
	@ExtendWith(OutputCaptureExtension.class)
	public void shouldNotTraceWhenEnteringAndExitingNormallyRestCallInProduction(CapturedOutput logs)
	{
		forceLogLevel(loggingSystem, TARGET_LOGGER, WARN);
		performCall("LC01");

		then(logs)//
				.doesNotContain(EXPECTED_TRACE_RIGHT_CALL_DEBUG_ENTRY)//
				.doesNotContain(EXPECTED_TRACE_RIGHT_CALL_DEBUG_EXIT);
	}

	@Test
	@ExtendWith(OutputCaptureExtension.class)
	public void shouldTraceErrorWhenRestCallIsThrowingOnProduction(CapturedOutput logs)
	{
		forceLogLevel(loggingSystem, TARGET_LOGGER, WARN);
		performCall("WHATEVER");

		then(logs)//
				.doesNotContain(EXPECTED_TRACE_WRONG_CALL_DEBUG_ENTRY)//
				.doesNotContain(EXPECTED_TRACE_WRONG_CALL_DEBUG_ERROR)//
				.contains(EXPECTED_TRACE_WRONG_CALL_ERROR);
	}

	@Test
	@ExtendWith(OutputCaptureExtension.class)
	public void shouldTraceWhenEnteringAndExitingNormallyRestCallForDebugging(CapturedOutput logs)
	{
		performCall("LC01");

		then(logs)//
				.contains(EXPECTED_TRACE_RIGHT_CALL_DEBUG_ENTRY)//
				.contains(EXPECTED_TRACE_RIGHT_CALL_DEBUG_EXIT);
	}

	@Test
	@ExtendWith(OutputCaptureExtension.class)
	public void shouldTraceWhenEnteringAndExitingWithErrorRestCallForDebugging(CapturedOutput logs)
	{
		performCall("WHATEVER");

		then(logs)//
				.contains(EXPECTED_TRACE_WRONG_CALL_DEBUG_ENTRY)//
				.contains(EXPECTED_TRACE_WRONG_CALL_DEBUG_ERROR)//
				.doesNotContain(EXPECTED_TRACE_WRONG_CALL_ERROR);
	}

}

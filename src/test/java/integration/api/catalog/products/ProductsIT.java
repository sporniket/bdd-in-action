package integration.api.catalog.products;

import static java.lang.String.format;
import static org.assertj.core.api.BDDAssertions.then;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.sporniket.littlecauldron.api.catalog.ApiCatalogApplication;
import com.sporniket.littlecauldron.api.catalog.ResourceError;
import com.sporniket.littlecauldron.api.catalog.ResourceProduct;
import com.sporniket.littlecauldron.utils.JsonHelper;

@SpringBootTest(classes = ApiCatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsIT
{
	private static final String FORMAT_REST_CALL_URL = "http://localhost:%s%s";

	private static final String FORMAT_REST_CALL_PATH = "/catalog/products/%s";

	@LocalServerPort
	int randomServerPort;

	private String restCallPath;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@PostConstruct
	public void finishInitialization()
	{
		restCallPath = format(FORMAT_REST_CALL_URL, randomServerPort, FORMAT_REST_CALL_PATH);
	}

	private ResponseEntity<String> performCall(String idProduct)
	{
		final String effectiveCall = format(restCallPath, idProduct);
		return restTemplate.getForEntity(effectiveCall, String.class);
	}

	@Test
	public void shouldReturnExistingProductData() throws Exception
	{
		ResponseEntity<String> response = performCall("LC01");

		then(response.getStatusCode().value()).isEqualTo(200);
		ResourceProduct actualProduct = JsonHelper.objectFrom(response.getBody(), ResourceProduct.class);
		then(actualProduct.getId()).isEqualTo("LC01");
		then(actualProduct.getLabel()).isEqualTo("Standard Quality");
	}

	@Test
	public void shouldReturnErrorOnProductNotFound() throws Exception
	{
		ResponseEntity<String> response = performCall("WHATEVER");

		then(response.getStatusCode().value()).isEqualTo(404);
		ResourceError actualError = JsonHelper.objectFrom(response.getBody(), ResourceError.class);
		then(actualError.getError()).isEqualTo("1001");
		then(actualError.getReason()).isEqualTo("Product 'WHATEVER' not found.");
	}
}

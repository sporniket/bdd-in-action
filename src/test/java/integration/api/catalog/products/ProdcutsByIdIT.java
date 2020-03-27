package integration.api.catalog.products;

import static java.lang.String.format;
import static org.assertj.core.api.BDDAssertions.then;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.sporniket.littlecauldron.api.catalog.ResourceError;
import com.sporniket.littlecauldron.api.catalog.ResourceProduct;
import com.sporniket.littlecauldron.api.catalog.ResourceProducts;
import com.sporniket.littlecauldron.utils.JsonHelper;

import integration.IntegrationTest;

public class ProdcutsByIdIT extends IntegrationTest
{
	private static final String FORMAT_REST_CALL_PATH_PRODUCT_BY_ID = "/catalog/products/%s";

	private static final String FORMAT_REST_CALL_PATH_PRODUCTS = "/catalog/products";

	private static final String FORMAT_REST_CALL_URL = "http://localhost:%s%s";

	private String restCallPathProductById;

	private String restCallPathProducts;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@PostConstruct
	public void finishInitialization()
	{
		restCallPathProductById = format(FORMAT_REST_CALL_URL, randomServerPort, FORMAT_REST_CALL_PATH_PRODUCT_BY_ID);
		restCallPathProducts = format(FORMAT_REST_CALL_URL, randomServerPort, FORMAT_REST_CALL_PATH_PRODUCTS);
	}

	private ResponseEntity<String> performCall()
	{
		final String effectiveCall = restCallPathProducts;
		return restTemplate.getForEntity(effectiveCall, String.class);
	}

	private ResponseEntity<String> performCallById(String idProduct)
	{
		final String effectiveCall = format(restCallPathProductById, idProduct);
		return restTemplate.getForEntity(effectiveCall, String.class);
	}

	@Test
	public void shouldReturnAllExistingProductData() throws Exception
	{
		ResponseEntity<String> response = performCall();

		then(response.getStatusCode().value()).isEqualTo(200);
		ResourceProducts actualProducts = JsonHelper.objectFrom(response.getBody(), ResourceProducts.class);
		then(actualProducts.getProducts()).isNotEmpty();
		then(actualProducts.getProducts().size()).isEqualTo(1);
		ResourceProduct actualProduct = actualProducts.getProducts().get(0);
		then(actualProduct.getId()).isEqualTo("LC01");
		then(actualProduct.getLabel()).isEqualTo("Standard Quality");
	}

	@Test
	public void shouldReturnErrorOnProductNotFound() throws Exception
	{
		ResponseEntity<String> response = performCallById("WHATEVER");

		then(response.getStatusCode().value()).isEqualTo(404);
		ResourceError actualError = JsonHelper.objectFrom(response.getBody(), ResourceError.class);
		then(actualError.getError()).isEqualTo("1001");
		then(actualError.getReason()).isEqualTo("Product 'WHATEVER' not found.");
	}

	@Test
	public void shouldReturnExistingProductData() throws Exception
	{
		ResponseEntity<String> response = performCallById("LC01");

		then(response.getStatusCode().value()).isEqualTo(200);
		ResourceProduct actualProduct = JsonHelper.objectFrom(response.getBody(), ResourceProduct.class);
		then(actualProduct.getId()).isEqualTo("LC01");
		then(actualProduct.getLabel()).isEqualTo("Standard Quality");
	}
}

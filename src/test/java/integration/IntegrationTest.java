package integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.sporniket.libre.test.dbunit.DbUnitExtensionSimplyDestructive;
import com.sporniket.littlecauldron.api.catalog.ApiCatalogApplication;

@SpringBootTest(classes = ApiCatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(DbUnitExtensionSimplyDestructive.class)
public class IntegrationTest
{

	@LocalServerPort
	protected int randomServerPort;

}

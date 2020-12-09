package br.com.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.algafood.domain.model.Cozinha;
import br.com.algafood.domain.repository.CozinhaRepository;
import br.com.algafood.util.DatabaseCleaner;
import br.com.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired

	private DatabaseCleaner databaseCleaner;
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private int quantCozinhas;
	
	private Cozinha cozinhaAmericana;

	private String jsonCorretoCozinhaChinesa;
	
	private static int COZINHA_ID_INEXISTENTE = 100;
	

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		databaseCleaner.clearTables();
		prepararBaseDados();
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		RestAssured.given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarRespostaEstatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given()
			.param("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("{/cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarEstatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.param("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("{/cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
			
	}


	public void deveTerNumeroExatoCozinhas_QuandoConsultarCozinhas() {

	
		RestAssured.given().accept(ContentType.JSON).when().get().then().body("", Matchers.hasSize(quantCozinhas));
//			.body("nome", hasItems("Indiana", "Tailandesa"));
	}

	
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given().body(jsonCorretoCozinhaChinesa).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.CREATED.value());

	}

	public void prepararBaseDados() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Tailandesa");
		cozinhaRepository.save(cozinha);
		
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		quantCozinhas = (int)cozinhaRepository.count();

		
	}
}

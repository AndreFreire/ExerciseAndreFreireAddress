package com.exercicioandrefreire;
import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CepControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getCepTest() {
		String body = this.restTemplate.getForObject("/cep/02256000/", String.class);
		JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(body);
			assertThat(jsonBody.get("zipcode")).isEqualTo("02256-000");
			assertThat(jsonBody.get("city")).isEqualTo("São Paulo");
			assertThat(jsonBody.get("street")).isEqualTo("Rua das Vertentes");
			assertThat(jsonBody.get("district")).isEqualTo("Vila Constança");
			assertThat(jsonBody.get("state")).isEqualTo("SP");
		} catch (JSONException e) {
			assertThat(false);			
		}
	}
	@Test
	public void getInvalidCepTest() {
		String body = this.restTemplate.getForObject("/cep/0225600a/", String.class);
		JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(body);
			assertThat(jsonBody.get("message")).isEqualTo("Invalid zipcode");
		} catch (JSONException e) {
			assertThat(false);			
		}
	}

}
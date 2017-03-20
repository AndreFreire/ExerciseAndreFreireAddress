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
public class ErrorsControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void Error404Test() {
		String body = this.restTemplate.getForObject("/xx/", String.class);
		JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(body);
			assertThat(jsonBody.get("message")).isEqualTo("Page not found");
		} catch (JSONException e) {
			assertThat(true).isEqualTo(false);			
		}
	}
}
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.services.AddressService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddressControllTest {

	private AddressService addressService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@Test
	public void getAddresTest() {
		int idSavedAddress = saveAddressTest();
		String body = this.restTemplate.getForObject(String.format(
				"/address/get/%s/", idSavedAddress), String.class);
		JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(body);
			assertThat(jsonBody.get("zipcode")).isEqualTo("02256-000");
			assertThat(jsonBody.get("city")).isEqualTo("São Paulo");
			assertThat(jsonBody.get("street")).isEqualTo("Rua das Vertentes");
			assertThat(jsonBody.get("district")).isEqualTo("Vila Constança");
			assertThat(jsonBody.get("state")).isEqualTo("SP");
		} catch (JSONException e) {
			assertThat(true).isEqualTo(false);			
		}
		this.addressService.deleteAddress(idSavedAddress);
	}
	
	@Test
	public void deleteAddresTest() {
		int idSavedAddress = saveAddressTest();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(String.valueOf(idSavedAddress));
		
		ResponseEntity<String> responseEntity = this.restTemplate.exchange(String.format(
				"/address/delete/%s/", idSavedAddress), HttpMethod.DELETE, httpEntity, String.class);
		
		String body = responseEntity.getBody();
	
		JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(body);
			assertThat(jsonBody.get("message")).isEqualTo("Address deleted successfully");
		} catch (JSONException e) {
			assertThat(true).isEqualTo(false);			
		}
		Address deletedAddress = this.addressService.getAddressById(idSavedAddress);
		assertThat(deletedAddress).isEqualTo(null);
	}
	@Test
	public void saveAddresTest() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("zipcode", "02256080");
		map.add("number", "789");
				
		String body = this.restTemplate.postForObject("/address/save/", map, String.class);
		JSONObject jsonBody;
		int idSavedAddress = 0;
		try {
			jsonBody = new JSONObject(body);
			idSavedAddress = jsonBody.getInt("id");
		} catch (JSONException e) {
			assertThat(true).isEqualTo(false);			
		}
		
		Address address = addressService.getAddressById(idSavedAddress);

		assertThat(address.getNumber()).isEqualTo("789");
		assertThat(address.getZipcode()).isEqualTo("02256-080");
		
		this.addressService.deleteAddress(idSavedAddress);		
	}
	
	@Test
	public void updateAddresTest() {
		int idSavedAddress = saveAddressTest();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("zipcode", "02256000");
		map.add("number", "789");
		map.add("id", String.valueOf(idSavedAddress));
				
		this.restTemplate.postForObject("/address/update/", map, String.class);
		
		Address address = addressService.getAddressById(idSavedAddress);
		assertThat(address.getNumber()).isEqualTo("789");
		assertThat(address.getZipcode()).isEqualTo("02256-000");
		
		this.addressService.deleteAddress(idSavedAddress);		
	}
	
	public int saveAddressTest(){
		Address address = new Address();
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet("Rua das Vertentes");
		address.setNumber("123");
		address.setZipcode("02256-000");
		address.setDistrict("Vila Constança");
		Address savedAddress = this.addressService.saveAddress(address);
		return savedAddress.getId();
	}
}
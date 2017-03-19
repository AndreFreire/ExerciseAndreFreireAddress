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
			assertThat(false);			
		}
		this.addressService.deleteAddress(idSavedAddress);
	}
	
	@Test
	public void deleteAddresTest() {
		int idSavedAddress = saveAddressTest();
		String body = this.restTemplate.getForObject(String.format(
				"/address/delete/%s/", idSavedAddress), String.class);
		JSONObject jsonBody;
		try {
			jsonBody = new JSONObject(body);
			assertThat(jsonBody.get("message")).isEqualTo("Address deleted successfully");
		} catch (JSONException e) {
			assertThat(false);			
		}
		Address deletedAddress = this.addressService.getAddressById(idSavedAddress);
		assertThat(deletedAddress == null);
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
			assertThat(false);			
		}
		
		Address address = addressService.getAddressById(idSavedAddress);
		assertThat(address.getNumber().equals("789"));
		assertThat(address.getZipcode().equals("02256-000"));
		
		this.addressService.deleteAddress(idSavedAddress);		
	}
	
	@Test
	public void updateAddresTest() {
		int idSavedAddress = saveAddressTest();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("zipcode", "02256080");
		map.add("number", "789");
				
		this.restTemplate.postForObject("/address/update/", map, String.class);
		
		Address address = addressService.getAddressById(idSavedAddress);
		assertThat(address.getNumber().equals("789"));
		assertThat(address.getZipcode().equals("02256-080"));
		
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
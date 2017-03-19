package com.exercicioandrefreire.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.services.AddressService;
import com.exercicioandrefreire.services.CepService;
import com.exercicioandrefreire.util.Util;

@RestController
@RequestMapping(value="address")
public class AddressController {
	private AddressService addressService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private CepService cepService;

	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@Autowired
	public void setCepService(CepService cepService) {
		this.cepService = cepService;
	}

	@RequestMapping(value = "/save/", method = RequestMethod.POST)
	public ResponseEntity<String> saveAddress(
			@RequestParam("zipcode") String zipcode, 
			@RequestParam("number") String number,
			@RequestParam(value = "complement", required = false) String complement
			){
		log.info("Starting save address");
		
		if(!Util.isNumber(number)){
			return Util.getResponseInvalid("Invalid Number");
		}
		if(!Util.isZipcode(zipcode)){
			return Util.getResponseInvalid("Invalid zipcode");
		}
		Address address = cepService.getAddressFromWebService(zipcode);
		if(address == null){
			return Util.getResponseNotFound("Zipcode not found");
		}
		address.setNumber(number);
		if(complement != null){
			address.setComplement(complement);
		}
		addressService.saveAddress(address);
		return Util.getResponseOk("Address created successfully", address.getId());
	}
	
	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public ResponseEntity<String> updateAddress(@RequestParam String id, 
												@RequestParam String zipcode,
												@RequestParam String number,
												@RequestParam(value = "complement", required = false) String complement){

		log.info("Starting update address");
		if(!Util.isNumber(id)){
			return Util.getResponseInvalid("Invalid id");
		}
		int idAddress = Integer.parseInt(id);
		Address address = addressService.getAddressById(idAddress);
		if(address == null){
			return Util.getResponseNotFound("Address not found");
		}
		if(!address.getZipcode().equals(zipcode)){
			address = cepService.getAddressFromWebService(zipcode);
			if(address == null){
				return Util.getResponseNotFound("Zipcode not found");
			}
		}
		if(complement != null){
			address.setComplement(complement);
		}
		address.setNumber(number);
		address.setId(idAddress);
		addressService.saveAddress(address);
		return Util.getResponseOk("Address update successfully", address.getId());
	}

	@RequestMapping(value = "/delete/{id}/", method = RequestMethod.GET)
	public ResponseEntity<String> deleteAddress(@PathVariable("id") String id ){
		log.info("Starting delete address");
		if(!Util.isNumber(id)){
			return Util.getResponseInvalid("Invalid id");
		}
		int idAddress = Integer.parseInt(id);
		try{
			addressService.deleteAddress(idAddress);
		} catch (EmptyResultDataAccessException e) {
			return Util.getResponseNotFound("Address not found");
		}
		return Util.getResponseOk("Address deleted successfully");
	}

	@RequestMapping(value = "/get/{id}/", method = RequestMethod.GET)
	public ResponseEntity<String> getAddressById(@PathVariable("id") String id ){
		log.info("Starting get address");
		if(!Util.isNumber(id)){
			return Util.getResponseInvalid("Invalid id");
		}
		int idAddress = Integer.parseInt(id);
		Address address = addressService.getAddressById(idAddress);
		if(address == null){
			return Util.getResponseNotFound("Address not found");
		}
		return Util.getAddressResponseOk(address);
	}    
}

package com.exercicioandrefreire.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.services.CepService;
import com.exercicioandrefreire.util.Util;

@RestController
@RequestMapping(value="cep")
class CepController{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private CepService cepService;
	
	@Autowired
    public void setCepService(CepService cepService) {
        this.cepService = cepService;
    }
	
	@RequestMapping(value="/{zipcode}/", method = RequestMethod.GET)
	public ResponseEntity<?> getAdress(@PathVariable String zipcode){
		log.info("Starting query zipcode");
		try{
			if(!Util.isZipcode(zipcode)){
				return Util.getResponseInvalid("Invalid zipcode");
			}
			Address address = cepService.getAddressFromWebService(zipcode);			
			if(address == null){
				return Util.getResponseNotFound("Zipcode not found");
			}	
			return Util.getAddressResponseOk(address);
			
		}catch (Exception e) {
			return Util.getResponseInternalError();
		}
	}
}
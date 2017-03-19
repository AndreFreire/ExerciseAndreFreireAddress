package com.exercicioandrefreire.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercicioandrefreire.util.Util;


@RestController
public class ErrorsController implements ErrorController{

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public ResponseEntity<String> error(HttpServletRequest request, HttpServletResponse response){
		
		switch (response.getStatus()) {
		case 404:
			return Util.getResponseNotFound("Page not found");
		case 400:
			return Util.getResponseInvalid("Bad request");			
		default:
			return Util.getResponseInternalError();
		}
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}  

}


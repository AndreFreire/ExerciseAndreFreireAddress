package com.exercicioandrefreire.domain;

import javax.persistence.*;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String zipcode;
	String number;
	String street;
	String district;
	String city;
	String state;
	String complement;

	public Address(String zipcode, String street, String district, String city, String state) {
		this.zipcode = zipcode;
		this.street = street;
		this.district = district;
		this.city = city;
		this.state = state;
	}

	public Address() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Override
	public String toString(){
		JSONObject response = new JSONObject();
		try {
			response.put("zipcode", this.getZipcode());
			response.put("street", this.getStreet());
			response.put("district", this.getDistrict());
			response.put("city", this.getCity());
			response.put("state", this.getState());
			if(this.getId() != 0){
				response.put("id", this.getId());
			}
			if(this.getNumber() != null){
				response.put("number", this.getNumber());
			}
			if(this.getComplement() != null){
				response.put("complement", this.getComplement());
			}

			return response.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	
}

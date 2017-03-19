package com.exercicioandrefreire.services;

import com.exercicioandrefreire.domain.Address;

public interface AddressService {

	Address getAddressById(Integer id);

	Address saveAddress(Address address);
	
	void deleteAddress(Integer id);
	
	Address updateAddress(Address address);
}
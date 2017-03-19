package com.exercicioandrefreire.repositories;

import org.springframework.data.repository.CrudRepository;

import com.exercicioandrefreire.domain.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{
	
}
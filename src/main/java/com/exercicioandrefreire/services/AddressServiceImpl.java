package com.exercicioandrefreire.services;
import com.exercicioandrefreire.domain.Address;
import com.exercicioandrefreire.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getAddressById(Integer id) {
        return addressRepository.findOne(id);
    }

    @Override
    public Address saveAddress(Address address) {
    	return addressRepository.save(address);
    }

	@Override
	public void deleteAddress(Integer id) {
		addressRepository.delete(id);				
	}
	
	@Override
	public Address updateAddress(Address address){
		return null;
	}
}
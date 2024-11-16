package com.authentication.auth_service.service;


import com.authentication.auth_service.entity.Address;
import com.authentication.auth_service.models.AddressDto;
import com.authentication.auth_service.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .addressLine(addressDto.getAddressLine())
                .state(addressDto.getState())
                .city(addressDto.getCity())
                .zipCode(addressDto.getZip())
                .build();
        address = addressRepository.save(address);
        return address;
    }
}

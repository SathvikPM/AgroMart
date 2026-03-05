package com.AgroMart.Backend.service;

import com.AgroMart.Backend.dto.AddressRequestDTO;
import com.AgroMart.Backend.dto.AddressResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    AddressResponseDTO createAddress(AddressRequestDTO addressRequest);

    List<AddressResponseDTO> getAllAddress();

    AddressResponseDTO getAddressById(Long id);

    AddressResponseDTO updateAddress(Long id,AddressRequestDTO addressRequest);

    void deleteAddress(Long id);


}

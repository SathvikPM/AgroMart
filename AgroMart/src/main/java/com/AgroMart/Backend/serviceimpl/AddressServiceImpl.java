package com.AgroMart.Backend.serviceimpl;

import com.AgroMart.Backend.repository.AddressRepository;
import com.AgroMart.Backend.service.AddressService;
import com.AgroMart.Backend.dto.AddressRequestDTO;
import com.AgroMart.Backend.dto.AddressResponseDTO;
import com.AgroMart.Backend.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper){

        this.addressRepository=addressRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public AddressResponseDTO createAddress(AddressRequestDTO addressRequest) {
//        Address address=new Address();
//        address.setStreet(addressRequest.getStreet());
//        address.setCity(addressRequest.getCity());
//        address.setState(addressRequest.getState());
//        address.setCountry(addressRequest.getCountry());
//        address.setPincode(addressRequest.getPincode());
//        address.setType(addressRequest.getType());
//        Address saved=addressRepository.save(address);
//
//        AddressResponseDTO addressResponse=new AddressResponseDTO();
//        addressResponse.setId(saved.getId());
//        addressResponse.setStreet(saved.getStreet());
//        addressResponse.setCity(saved.getCity());
//        addressResponse.setState(saved.getState());
//        addressResponse.setCountry(saved.getCountry());
//        addressResponse.setPincode(saved.getPincode());
//        addressResponse.setType(saved.getType());


        Address address=modelMapper.map(addressRequest,Address.class);
        Address saved=addressRepository.save(address);
        AddressResponseDTO addressResponse=modelMapper.map(saved,AddressResponseDTO.class);

        return addressResponse;


    }


    @Override
    public List<AddressResponseDTO> getAllAddress() {
        List<Address> addresses=addressRepository.findAll();
//        return addresses.stream().map(a->{
//            AddressResponseDTO addressResponse=new AddressResponseDTO();
//            addressResponse.setId(a.getId());
//            addressResponse.setStreet(a.getStreet());
//            addressResponse.setType(a.getType());
//            addressResponse.setState(a.getState());
//            addressResponse.setPincode(a.getPincode());
//            addressResponse.setCity(a.getCity());
//            addressResponse.setCountry(a.getCountry());
//            return addressResponse;
//        }).collect(Collectors.toList());
        return addresses.stream()
                .map(a -> modelMapper.map(a, AddressResponseDTO.class))
                .collect(Collectors.toList());


    }

    @Override
    public AddressResponseDTO getAddressById(Long id) {

        Address address=addressRepository.findById(id).orElseThrow(()->new RuntimeException("Address not found with id: "+id));
        AddressResponseDTO addressResponse=modelMapper.map(address,AddressResponseDTO.class);
        return  addressResponse;
    }

    @Override
    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO addressRequest) {
        Address address=addressRepository.findById(id).orElseThrow(()->new RuntimeException("address not found with id"+id));
        address.setType(addressRequest.getType());
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setState(addressRequest.getState());
        address.setPincode(addressRequest.getPincode());
        address.setCountry(addressRequest.getCountry());
        Address updated=addressRepository.save(address);

        AddressResponseDTO addressResponse=modelMapper.map(updated,AddressResponseDTO.class);
        return  addressResponse;
    }

    @Override
    public void deleteAddress(Long id) {
                 Address address=addressRepository.findById(id).orElseThrow(()->
                         new RuntimeException("Address not found with id: "+ id));
                 addressRepository.delete(address);
    }
}

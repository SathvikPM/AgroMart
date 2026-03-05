package com.AgroMart.Backend.controller;


import com.AgroMart.Backend.dto.AddressRequestDTO;
import com.AgroMart.Backend.dto.AddressResponseDTO;
import com.AgroMart.Backend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    public  final AddressService addressService;
    public AddressController(AddressService addressService){

        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponseDTO>  createAddress(@Valid @RequestBody AddressRequestDTO addressRequest){

        AddressResponseDTO addressResponse=addressService.createAddress(addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddress(){
        List<AddressResponseDTO> allAddress=addressService.getAllAddress();
        return ResponseEntity.ok(allAddress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id){
        AddressResponseDTO addressResponse=addressService.getAddressById(id);
        return  ResponseEntity.ok(addressResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long id,@RequestBody AddressRequestDTO addressRequest){
        AddressResponseDTO addressResponse=addressService.updateAddress(id,addressRequest);
        return  ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address Delete Sucessfully");
    }



}

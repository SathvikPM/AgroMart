package com.AgroMart.Backend.dto;


import com.AgroMart.Backend.model.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private AddressType type;

}

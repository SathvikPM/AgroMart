package com.AgroMart.Backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String country;
    @NotNull
     private String pincode;

    @Enumerated(EnumType.STRING)
    @NotNull
     private AddressType type;

}

package com.AgroMart.Backend.dto;

import com.AgroMart.Backend.model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Role role;

    private Boolean enabled;

    private String profileImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package com.AgroMart.Backend.service;

import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponseDTO  createUser(UserRequestDTO request, MultipartFile profileImage);
}

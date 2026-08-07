package com.AgroMart.Backend.service;

import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponseDTO  createUser(UserRequestDTO request, MultipartFile profileImage);

    public List<UserResponseDTO> getAllUsers();

    public UserResponseDTO getUserById(Long id);


    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, MultipartFile profileImage);

    UserResponseDTO patchUser(Long id, UserRequestDTO userRequestDTO, MultipartFile profileImage);

    void deleteUser(Long id);
}

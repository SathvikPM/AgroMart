package com.AgroMart.Backend.serviceimpl;

import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import com.AgroMart.Backend.model.User;
import com.AgroMart.Backend.repository.UserRepository;
import com.AgroMart.Backend.service.FileStorageService;
import com.AgroMart.Backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    public  UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper,FileStorageService fileStorageService){

        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
        this.fileStorageService=fileStorageService;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO request, MultipartFile profileImage) {
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new RuntimeException("Email Alredy Exixts");
        }
        if(userRepository.existsByPhone(request.getPhone())){
            throw new RuntimeException("Phone Number Alredy Exixts");
        }
        User user=modelMapper.map(request, User.class);

        if(profileImage != null && !profileImage.isEmpty()){
            String imageUrl=fileStorageService.saveFile(profileImage,"users");
            user.setProfileImage(imageUrl);
        }

        User savedUser=userRepository.save(user);

        return modelMapper.map(savedUser,UserResponseDTO.class);


    }
}

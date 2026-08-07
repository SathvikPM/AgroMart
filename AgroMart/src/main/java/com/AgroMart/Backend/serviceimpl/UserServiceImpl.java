package com.AgroMart.Backend.serviceimpl;

import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import com.AgroMart.Backend.model.User;
import com.AgroMart.Backend.repository.UserRepository;
import com.AgroMart.Backend.service.FileStorageService;
import com.AgroMart.Backend.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

        @Override
       public List<UserResponseDTO> getAllUsers() {
        List<User> users=userRepository.findAll();

        return users.stream().map(user->
            modelMapper.map(user,UserResponseDTO.class)).toList();

        }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        UserResponseDTO userResponseDTO=modelMapper.map(user,UserResponseDTO.class);
        return userResponseDTO;

    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, MultipartFile profileImage) {

        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
        if(user.getEmail().equalsIgnoreCase(userRequestDTO.getEmail()) && userRepository.existsByEmailIgnoreCase(userRequestDTO.getEmail()) )
        {
            throw new RuntimeException("Email Already Exists");
        }
        if(user.getPhone().equals(userRequestDTO.getPhone()) && userRepository.existsByPhone(userRequestDTO.getPhone()))
        {
            throw new RuntimeException("Phone Already Exists");
        }

        if(profileImage != null && !profileImage.isEmpty()){
            fileStorageService.deleteFile(user.getProfileImage());
            String imageUrl=fileStorageService.saveFile(profileImage,"users");
            user.setProfileImage(imageUrl);
        }

        modelMapper.map(userRequestDTO,user);

        User updatedUser=userRepository.save(user);

        UserResponseDTO userResponseDTO=modelMapper.map(updatedUser,UserResponseDTO.class);

        return userResponseDTO;

    }

    @Override
    @Transactional
    public UserResponseDTO patchUser(Long id,
                                     UserRequestDTO request,
                                     MultipartFile profileImage) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getEmail() != null &&
                !user.getEmail().equalsIgnoreCase(request.getEmail()) &&
                userRepository.existsByEmailIgnoreCase(request.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        if (request.getPhone() != null &&
                !user.getPhone().equals(request.getPhone()) &&
                userRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone already exists");
        }

        if (profileImage != null && !profileImage.isEmpty()) {

            if (user.getProfileImage() != null) {
                fileStorageService.deleteFile(user.getProfileImage());
            }

            String imageUrl = fileStorageService.saveFile(profileImage, "users");

            user.setProfileImage(imageUrl);
        }

        modelMapper.map(request, user);

        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException());
        fileStorageService.deleteFile(user.getProfileImage());
        userRepository.delete(user);
    }
}

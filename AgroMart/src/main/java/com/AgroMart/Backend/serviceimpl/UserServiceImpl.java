package com.AgroMart.Backend.serviceimpl;

import com.AgroMart.Backend.exception.DuplicateResourceException;
import com.AgroMart.Backend.exception.ResourceNotFoundException;
import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import com.AgroMart.Backend.model.User;
import com.AgroMart.Backend.repository.UserRepository;
import com.AgroMart.Backend.security.PasswordConfig;
import com.AgroMart.Backend.service.FileStorageService;
import com.AgroMart.Backend.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    private final PasswordEncoder passwordEncoder; //object returned from your PasswordConfig class is injected

    public  UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper,FileStorageService fileStorageService, PasswordEncoder passwordEncoder){

        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
        this.fileStorageService=fileStorageService;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO request, MultipartFile profileImage) {
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new DuplicateResourceException("Email Already Exists");
        }
        if(userRepository.existsByPhone(request.getPhone())){
            throw new DuplicateResourceException("Phone Number Already Exists");
        }
        User user=modelMapper.map(request, User.class);

        String password=user.getPassword();
        user.setPassword(passwordEncoder.encode(password)); //password get hashed ans stored in the DB

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

//        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        ));
        UserResponseDTO userResponseDTO=modelMapper.map(user,UserResponseDTO.class);
        return userResponseDTO;

    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, MultipartFile profileImage) {

        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found by id "+ id));
        if(user.getEmail().equalsIgnoreCase(userRequestDTO.getEmail()) && userRepository.existsByEmailIgnoreCase(userRequestDTO.getEmail()) )
        {
            throw new DuplicateResourceException("Email Already Exists");
        }
        if(user.getPhone().equals(userRequestDTO.getPhone()) && userRepository.existsByPhone(userRequestDTO.getPhone()))
        {
            throw new DuplicateResourceException("Phone Already Exists");
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

            throw new DuplicateResourceException("Email already exists");
        }

        if (request.getPhone() != null &&
                !user.getPhone().equals(request.getPhone()) &&
                userRepository.existsByPhone(request.getPhone())) {

            throw new DuplicateResourceException("Phone already exists");
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
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Does Not Exist By: "+id));
        fileStorageService.deleteFile(user.getProfileImage());
        userRepository.delete(user);
    }
}

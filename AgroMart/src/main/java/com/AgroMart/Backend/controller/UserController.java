package com.AgroMart.Backend.controller;



import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import com.AgroMart.Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestPart("user") UserRequestDTO request,
                                                        @RequestPart(value = "profileImage",required = false)
                                                                                   MultipartFile profileImage){

        UserResponseDTO response=userService.createUser(request,profileImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


}

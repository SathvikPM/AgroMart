package com.AgroMart.Backend.controller;



import com.AgroMart.Backend.dto.UserRequestDTO;
import com.AgroMart.Backend.dto.UserResponseDTO;
import com.AgroMart.Backend.model.User;
import com.AgroMart.Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }


    @GetMapping("/test-exception")
    public ResponseEntity<String> testException() {

        String value = null;

        // Intentional NullPointerException
        value.length();

        return ResponseEntity.ok("Success");
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestPart("user") UserRequestDTO request,
                                                        @RequestPart(value = "profileImage",required = false)
                                                                                   MultipartFile profileImage){

        UserResponseDTO response=userService.createUser(request,profileImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
     List<UserResponseDTO> users =userService.getAllUsers();
     return ResponseEntity.ok(users);


    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO>  getUserById(@PathVariable Long id){
                  UserResponseDTO user=userService.getUserById(id);
                  return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                      @RequestPart("user") UserRequestDTO userRequestDTO,
                                                      @RequestPart(value = "profileImage",required = false) MultipartFile profileImage)
    {

       UserResponseDTO updatedUser=userService.updateUser(id,userRequestDTO,profileImage);

       return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDTO> patchUser(
            @PathVariable Long id,

            @RequestPart("user") UserRequestDTO userRequestDTO,

            @RequestPart(value = "profileImage", required = false)
            MultipartFile profileImage) {

        UserResponseDTO response =
                userService.patchUser(id, userRequestDTO, profileImage);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted Successfully");
    }


}

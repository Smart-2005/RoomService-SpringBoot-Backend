package com.hotel.user.controller;

import com.hotel.user.dto.UserDTO;
import com.hotel.user.jwt.JwtService;
import com.hotel.user.model.User;
import com.hotel.user.service.UserService;
import com.hotel.user.util.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private  final JwtService jwtService;

    @PostMapping("users/register")
    private ResponseEntity<AuthenticationResponse> saveUser(
            @RequestBody UserDTO userDTO
    ){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("users/login")
    private ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserDTO userDTO
    ){
        return ResponseEntity.ok(userService.login(userDTO));
    }

    @GetMapping("users/getuser")
    private Optional<User> getUserByEmail(
            @RequestParam String email
    ){
        return userService.getUserByEmail(email);
    }
    @GetMapping("users/allusers")
    private List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("users")
    private UserDTO getUser(
            @RequestParam Integer userId
    ){
        return userService.getUserById(userId);
    }

    @DeleteMapping("users/delete")
    private ResponseEntity<String> deleteUser(
            @RequestParam Integer userId
    ){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

}

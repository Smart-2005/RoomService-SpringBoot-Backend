package com.hotel.user.service;

import com.hotel.user.dto.UserDTO;
import com.hotel.user.model.User;
import com.hotel.user.util.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthenticationResponse createUser(UserDTO userDTO);
    AuthenticationResponse login(UserDTO userDTO);
    Optional<User> getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Integer userId);
    void deleteUser(Integer userId);

}

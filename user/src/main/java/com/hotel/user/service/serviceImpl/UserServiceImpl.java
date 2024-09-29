package com.hotel.user.service.serviceImpl;

import com.hotel.user.dto.UserDTO;
import com.hotel.user.jwt.JwtService;
import com.hotel.user.model.User;
import com.hotel.user.model.UserRole;
import com.hotel.user.repository.UserRepository;
import com.hotel.user.service.UserService;
import com.hotel.user.util.AuthenticationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse createUser(UserDTO userDTO) {
        userDTO.setUserRole(UserRole.USER);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var savedUser = userRepository.save(modelMapper.map(userDTO, User.class));
        userDTO.setUserId(savedUser.getUserId());
        String token = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse login(UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getEmail(),
                            userDTO.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid email or password.", e);
        }
        User user = userRepository.getUserByEmail(userDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found."));
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<UserDTO> getAllUsers() {
       List<User> allUsers = userRepository.findAll();
       return modelMapper.map(allUsers,new TypeToken<List<UserDTO>>(){}.getType());
    }

    @Override
    public UserDTO getUserById(Integer userId) {
       User getUser = userRepository.getUserByUserId(userId);
        return modelMapper.map(getUser,new TypeToken<UserDTO>(){}.getType());
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}

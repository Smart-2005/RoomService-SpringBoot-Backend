package com.hotel.user.dto;

import com.hotel.user.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String fullname;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String password;
}

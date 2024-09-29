package com.hotel.user.repository;

import com.hotel.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * FROM user WHERE user_id = ?1",nativeQuery = true)
    User getUserByUserId(Integer userId);

    //@Query(value = "SELECT * FROM user WHERE email = ?1",nativeQuery = true)
    Optional<User> getUserByEmail(String email);
}

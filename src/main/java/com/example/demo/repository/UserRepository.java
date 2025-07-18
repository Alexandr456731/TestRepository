package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "FROM User WHERE login = :login")
    Optional<User> findByLogin(String login);

    @Query(value = "FROM User WHERE email = :email")
    Optional<User> findByEmail(String email);
}

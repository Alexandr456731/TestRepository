package com.example.demo.repository;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String login;
    private String password;
    private String name;
    @NonNull
    private String email;
    private Boolean isAdmin = false;
    private Boolean isBlocked = false;
    private BigInteger balance = BigInteger.ZERO;
}

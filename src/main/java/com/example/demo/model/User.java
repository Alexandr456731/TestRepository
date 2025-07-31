package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @Email
    private String email;
    private Boolean isAdmin = false;
    private Boolean isBlocked = false;
    private BigInteger balance = BigInteger.ZERO;
}

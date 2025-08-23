package com.example.demo.model;

import com.example.demo.annotation.Login;
import com.example.demo.annotation.Password;
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
    @Login
    private String login;
    @Password
    private String password;
    private String name;
    @Email
    private String email;
    private Boolean isAdmin = false;
    private Boolean isBlocked = false;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private BigInteger balance = BigInteger.ZERO;

    public long getBalance() {
        return balance.longValue();
    }

    public void setBalance(long money) {
        balance = BigInteger.valueOf(money);
    }

    public void addBalance(long money) {
        balance = balance.add(BigInteger.valueOf(money));
    }
}

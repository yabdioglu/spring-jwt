package com.example.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthController {
    List<User> users = new ArrayList<>();

    public AuthController() {
        users.add(User.builder().id(1).username("user1").password("P4ssword").role("user").build());
        users.add(User.builder().id(2).username("user2").password("P4ssword").role("admin").build());
    }

    @PostMapping("/api/1.0/auth")
    public ResponseEntity<?> auth(@RequestBody Credentials credentials) {
        Optional<User> optionalUser = users.stream().filter(u -> {
            return u.getUsername().equals(credentials.getUsername()) && u.getPassword().equals(credentials.getPassword());
        }).findFirst();

        if (!optionalUser.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

        User user = optionalUser.get();

        Date expireAt = new Date(System.currentTimeMillis() + 20*1000);

        String token = Jwts.builder().claim("username", user.getUsername())
                .claim("id", "" + user.getId())
                .signWith(SignatureAlgorithm.HS512, "my-app-secret")
                .setExpiration(expireAt)
                .compact();

        return ResponseEntity.ok(token);
    }
}

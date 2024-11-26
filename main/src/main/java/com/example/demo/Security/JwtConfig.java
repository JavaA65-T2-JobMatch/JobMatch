package com.example.demo.Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public Key getSecretKey() {
        return secretKey;
    }
}

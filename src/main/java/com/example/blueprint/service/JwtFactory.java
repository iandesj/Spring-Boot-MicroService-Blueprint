package com.example.blueprint.service;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import com.example.blueprint.User;

@Component
public class JwtFactory {

    private Key key = MacProvider.generateKey();
    private Calendar calendar = Calendar.getInstance();

    public String generateToken(User user) {
        calendar.add(Calendar.MINUTE, 30);
        return Jwts.builder()
                .setSubject("user")
                .setExpiration(calendar.getTime())
                .claim("admin", user.isAdmin())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

}

package com.woongeya.zoing.global.jwt.util;

import com.woongeya.zoing.domain.auth.domain.RefreshToken;
import com.woongeya.zoing.domain.auth.domain.repository.RefreshTokenRepository;
import com.woongeya.zoing.global.jwt.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.woongeya.zoing.global.jwt.config.JwtConstants.*;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(String authId, String role) {
        return jwtProperties.getPrefix() + EMPTY.getMessage() + generateToken(authId, role, ACCESS_KEY.getMessage(), jwtProperties.getAccessExp());
    }

    public String generateRefreshToken(String authId, String role) {
        String refreshToken = jwtProperties.getPrefix() + EMPTY.getMessage() + generateToken(authId, role, REFRESH_KEY.getMessage(), jwtProperties.getRefreshExp());

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(authId)
                        .token(refreshToken)
                        .ttl(jwtProperties.getRefreshExp())
                        .build()
        );

        return refreshToken;
    }

    private String generateToken(String authId, String role, String type, Long time) {
        return Jwts.builder()
                .setHeaderParam(TYPE.message, type)
                .claim(ROLE.message, role)
                .claim(AUTH_ID.message, authId)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .compact();
    }
}
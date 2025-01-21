package com.alura.foro.security;

import com.alura.foro.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro")
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(30).toInstant())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Integer getUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.require(algorithm)
                    .withIssuer("foro")
                    .build()
                    .verify(token)
                    .getClaim("id").asInt();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}

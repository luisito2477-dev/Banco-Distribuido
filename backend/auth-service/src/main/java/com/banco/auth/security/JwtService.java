package com.banco.auth.security;

import com.banco.auth.entity.Role;
import com.banco.auth.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class JwtService {

    //Inyectando valores desde el application.properties
    @Value("${banco.jwt.secret}")
    private String jwtSecret;

    @Value("${banco.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(User user){

        //informacion extra que viajara dentro del token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("name", user.getName());
        claims.put("curp", user.getCurp());
        claims.put("roles", user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList()));

        //Generando llave de firma criptografica
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)  //info personalizada
                .subject(user.getEmail()) //dueno del token
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)  //firmando criptograficamente con llave
                .compact();   //enpaquetando en string
    }
}

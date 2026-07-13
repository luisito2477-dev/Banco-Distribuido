package com.banco.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF (Cross-Site Request Forgery) porque se usara JWTs
                .csrf(csrf -> csrf.disable())

                // Configurar los permisos de las rutas
                .authorizeHttpRequests(auth -> auth
                        // Permitimos acceso  sin token a los endpoints de auth
                        .requestMatchers("/api/auth/**").permitAll()
                        // Cualquier otra peticion si requerira autenticacion
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}

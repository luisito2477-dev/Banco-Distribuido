package com.banco.auth.service;

import com.banco.auth.entity.Role;
import com.banco.auth.entity.User;
import com.banco.auth.repository.RoleRepository;
import com.banco.auth.repository.UserRepository;
import com.banco.auth.dto.RegisterRequest;
import com.banco.auth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    // dependencias
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;

    // clase que se testeara
    private UserService userService;


    @BeforeEach
    void setUp(){
        // Objetos clon/mock
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        jwtService = new JwtService();

        // Le inyectamos los @Value a mano al servicio real para el test
        ReflectionTestUtils.setField(jwtService, "jwtSecret", "LaClaveSecretaSuperLargaYSeguraParaElBancoDeLuisFernando2026!");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);

        userService = new UserService(userRepository, roleRepository, passwordEncoder, jwtService);
    }

    @Test
    void registrarUsuario_DeberiaTenerExito_CuandoDatosSonValidos() {
        // Preparacion escenario
        RegisterRequest request = new RegisterRequest();
        request.setName("Luis Fernando");
        request.setEmail("luis@banco.com");
        request.setPassword("PasswordSeguro123!");
        request.setCurp("HERL020515HMCXRS01");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByCurp(request.getCurp())).thenReturn(false);
        //when(passwordEncoder.encode(request.getPassword())).thenReturn("contraseña_encriptada_hash");

        Role rolMock = new Role("ROLE_CLIENT");
        when(roleRepository.findByRoleName("ROLE_CLIENT")).thenReturn(Optional.of(rolMock));

        // --- WHEN (Ejecución del metodo que queremos probar) ---
        String resultado = userService.registerUser(request);

        // --- THEN (Verificaciones de que todo salio bien) ---
        assertEquals("User created successfully.", resultado);

        // Verificamos que el repositorio realmente haya llamado al metodo .save() una vez
        verify(userRepository, times(1)).save(any(User.class));
    }
}

package com.banco.auth.service;

import com.banco.auth.dto.AuthResponse;
import com.banco.auth.dto.ForgotPasswordRequest;
import com.banco.auth.dto.LoginRequest;
import com.banco.auth.dto.RegisterRequest;
import com.banco.auth.repository.RoleRepository;
import com.banco.auth.repository.UserRepository;
import com.banco.auth.entity.User;
import com.banco.auth.entity.Role;
import com.banco.auth.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional  //Asegura que si algo falla, no se guarde a medias en la db
    public String registerUser(RegisterRequest request){

        //verificar que el correo no existe already
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email is already registered.");
        }

        //verificar que el curp no existe already
        if(userRepository.existsByCurp(request.getCurp())){
            throw new RuntimeException("CURP already registered.");
        }

        //creamos el objeto
        User newUser = new User(
                request.getCurp(),
                passwordEncoder.encode(request.getPassword()),  //password hasheada
                request.getEmail(),
                request.getName()
        );

        //obtenemos rol client
        Role roleClient = roleRepository.findByRoleName("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("Internal Error. ROLE_CLIENT does not exist in the database."));

        //anadimos rol
        newUser.getRoles().add(roleClient);

        //guardar en postgres
        userRepository.save(newUser);

        return "User created successfully.";
    }


    public AuthResponse loginUser(LoginRequest request){

        //Verificar que el usuario existe
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Incorrect Credentials"));

        //comparar passwords
        if (!passwordEncoder.matches(request.getPassword(), user.getHashedPassword())) {
            throw new RuntimeException("Incorrect Credentials.");
        }

        String token = jwtService.generateToken(user);

        List<String> listRoles = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        return new AuthResponse(token, user.getEmail(), listRoles);
    }


    public void forgotPasswordService(ForgotPasswordRequest request){
        //1) obtener email (podria ser que venga en la peticion {email: email@example.com})

        //2) verificar que el email existe
        if(!userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email does not exist.");
        }
        //3) generar codigo de recuperacion


        //4) Enviar correo de recuperacion (aqui se ocuparia del microservicio de mensajeria, por lo que no se podria hacer aun)
        //5) Enviar de respuesta que se ha enviado el correo exitosamente

    }

}

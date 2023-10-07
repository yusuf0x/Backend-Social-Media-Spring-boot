package com.social.app.controllers;

import com.social.app.config.JwtService;
import com.social.app.config.UserDetailsImpl;
import com.social.app.models.Person;
import com.social.app.models.User;
import com.social.app.payload.request.LoginRequest;
import com.social.app.payload.request.SignupRequest;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.JwtResponse;
import com.social.app.repositories.UserRepository;
import com.social.app.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PersonService personService;
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, PersonService personService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody SignupRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Error: Email is already in use!"));
        }
        int randomNumber = (int) (10000 + Math.random() * 90000);
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .tokenTemp(String.valueOf(randomNumber))
                .build();
        Person person = Person.builder()
                .fullname(request.getFullname())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        personService.savePerson(person);
        user.setPerson(person);
        userRepository.save(user);
        // send Email Verify with randomNumber
        return ResponseEntity.ok(new ApiResponse(true,"User registered successfully!"));
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        if (!userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Email not found !"));
        }
        User user = userRepository.findByEmail(request.getEmail()).get();
//        if(!user.isEmailVerified()){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new ApiResponse(false,"Verify Your Email Please"));
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.createToken(new HashMap<>(),request.getEmail());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse("Welcome",true,jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail()));
    }
}

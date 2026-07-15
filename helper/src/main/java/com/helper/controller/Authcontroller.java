package com.helper.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.helper.dto.signup.SignupRequest;
import com.helper.servirce.UserDtoService;
import com.helper.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import com.helper.dto.login.LoginRequest;
import com.helper.entity.Users;
import com.helper.Exception.GeneratingException.UserNotFoundException;
import com.helper.Exception.GeneratingException.InvalidPasswordException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;



@RestController
public class Authcontroller {
    
    private final UserDtoService userDtoService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    Authcontroller(UserDtoService userDtoService,JWTUtil jwtUtil,AuthenticationManager authenticationManager) {
        this.userDtoService = userDtoService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest)
    {
        userDtoService.registerUser(signupRequest);
        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
        );

        return ResponseEntity.ok().body(jwtUtil.getJwtToken(loginRequest.getEmail()));
    }

}

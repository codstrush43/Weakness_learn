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

@RestController
public class Authcontroller {
    
    private final UserDtoService userDtoService;
    private final JWTUtil jwtUtil;

    Authcontroller(UserDtoService userDtoService,JWTUtil jwtUtil) {
        this.userDtoService = userDtoService;
        this.jwtUtil = jwtUtil;
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
        Users user = userDtoService.findUserByEmail(loginRequest.getEmail());

        if(user==null)
        {
            throw new UserNotFoundException("User not found with email : " + loginRequest.getEmail());
        }
        if(!userDtoService.isPasswordMatch(loginRequest.getPassword(),user.getPassword()))
        {
            throw new InvalidPasswordException("Invalid password for email : " + loginRequest.getEmail());
        }
        return ResponseEntity.ok().body(jwtUtil.getJwtToken(user.getEmail()));
    }

}

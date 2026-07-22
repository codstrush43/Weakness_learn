package com.helper.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import com.helper.util.JWTUtil;

import org.springframework.http.ResponseEntity;

import com.helper.entity.Users;
import com.helper.servirce.UserDtoService;
import com.helper.Exception.GeneratingException.CodeforcesEmailNotMatchException;
import com.helper.dto.login.LoginRequest;
import com.helper.dto.signup.SignupRequest;
import com.helper.Exception.GeneratingException.InvalidPasswordException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.helper.Exception.GeneratingException.CodeforcesEmailNotFoundException;
import com.helper.Exception.GeneratingException.CodeforcesHandlerNotExist;
import com.helper.dto.codeforcesDto.CodeforcesResponse;
import com.helper.dto.codeforcesDto.CodeforcesResponse;
import com.helper.dto.codeforcesDto.CodeforcesUserInfo;
import com.helper.servirce.CodeforcesService;
 


@RestController
public class Authcontroller {

    private final UserDtoService userDtoService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CodeforcesService codeforcesService;


    public Authcontroller(UserDtoService userDtoService,JWTUtil jwtUtil,AuthenticationManager authenticationManager,CodeforcesService codeforcesService, CodeforcesController codeforcesController) {
        this.userDtoService = userDtoService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager=authenticationManager;
        this.codeforcesService=codeforcesService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest)
    {
        CodeforcesResponse codeforcesResponse=codeforcesService.getCodeforcesUserInfo(signupRequest.getCf_id()); 
        List<CodeforcesUserInfo> codeforcesUserInfo=codeforcesResponse.getResult();

        if(codeforcesUserInfo.get(0).getEmail()==null)
        {
            throw new CodeforcesEmailNotFoundException("Please Unhide Your Contect Information On Your Codeforces Account.");
        }
        if(!codeforcesUserInfo.get(0).getEmail().equals(signupRequest.getEmail()))
        {
            throw new CodeforcesEmailNotMatchException("Enter Email Which Is Registerede With Codeforces.");
        }
        userDtoService.registerUser(signupRequest,codeforcesUserInfo.get(0));
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
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(jwtUtil.getJwtToken(loginRequest.getEmail()));
    }

    @GetMapping("/student")
    public ResponseEntity<String> hii()
    {
        
        return ResponseEntity.ok().body("helo this is student!!");
    }

}
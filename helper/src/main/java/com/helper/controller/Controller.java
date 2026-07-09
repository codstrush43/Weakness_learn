package com.helper.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.helper.servirce.UserDtoService;
import com.helper.dto.SignupRequest;

@RestController
public class Controller {
    private final UserDtoService userDtoService;

    Controller(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequest signupRequest)
    {
        userDtoService.registerUser(signupRequest);
        return "User registered successfully";
    }
}

package com.helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.helper.dto.codeforcesDto.CodeforcesResponse;
import com.helper.servirce.CodeforcesService;

@RestController
public class CodeforcesController {

    @Autowired
    private CodeforcesService codeforcesService;

    // @GetMapping("/user/{handle}")
    // public ResponseEntity<CodeforcesResponse> userDetails(@PathVariable String handle)
    // {
        
    // }

}

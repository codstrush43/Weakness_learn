package com.helper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helper.entity.Codeforces;
import com.helper.entity.Tag;
import com.helper.servirce.CodeforcesService;

@RestController
public class CodeforcesController {

    @Autowired
    private CodeforcesService codeforcesService;


    @GetMapping("/reload")
    public ResponseEntity<String> userDetails()
    {
        return ResponseEntity.ok().body(codeforcesService.reload());
    }

    @GetMapping("/codeforces")
    public ResponseEntity<Codeforces> getCodeforces()
    {
        return ResponseEntity.ok().body(codeforcesService.getCodeforcesHandler());
    }

    @GetMapping("/weekness")
    public ResponseEntity<List<List<Tag>>> getWeekness()
    {
        return ResponseEntity.ok().body(codeforcesService.getWeekness());
    }

}

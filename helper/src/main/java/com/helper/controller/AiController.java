package com.helper.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helper.entity.Tag;
import com.helper.servirce.AiService;
import com.helper.servirce.CodeforcesService;

@RestController
public class AiController {
    
    private final CodeforcesService codeforcesService;
    private final AiService aiService;

    public AiController(CodeforcesService codeforcesService,AiService aiService)
    {
        this.codeforcesService=codeforcesService;
        this.aiService=aiService;
    }

    @GetMapping("/mcq")
    public ResponseEntity<String> getQuestions()
    {
        List<List<Tag>> weekness=codeforcesService.getWeekness();

        String mcqs=aiService.generateQuestion(weekness.get(0),weekness.get(1),weekness.get(2));
        return ResponseEntity.ok().body(mcqs);
    }

}

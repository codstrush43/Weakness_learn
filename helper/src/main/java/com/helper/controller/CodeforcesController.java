package com.helper.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.helper.dto.codeforcesDto.CodeforcesProblemInfo;
import com.helper.dto.codeforcesDto.CodeforcesSubmissionInfo;
import com.helper.dto.codeforcesDto.CodeforcesUserStatusInfo;
import com.helper.servirce.CodeforcesService;

@RestController
public class CodeforcesController {

    @Autowired
    private CodeforcesService codeforcesService;

    @GetMapping("/user/{handle}")
    public ResponseEntity<String> userDetails(@PathVariable String handle)
    {
        CodeforcesUserStatusInfo codeforcesUserStatusInfo=codeforcesService.getCodeforcesUserStatusInfo(handle);

        List<CodeforcesSubmissionInfo> codeforcesSubmissionInfos=codeforcesUserStatusInfo.getResult();

        Integer n=codeforcesSubmissionInfos.size();

        Map<String,Integer> preBuildTags=new HashMap<>();
        preBuildTags.put("dp",0);

        List<List<Integer>> tag_count=new ArrayList<>();
        tag_count.add(new ArrayList<>(Collections.nCopies(2, 0)));

        for(CodeforcesSubmissionInfo cf_info : codeforcesSubmissionInfos)
        {
            boolean isSolved=cf_info.getStatus().equals("OK")?true:false;
            CodeforcesProblemInfo codeforcesProblemInfo=cf_info.getProblem();
            List<String> tags=codeforcesProblemInfo.getTags();
            for(String tag:tags)
            {
                if(preBuildTags.get(tag)==null)
                    continue;
                if(isSolved)
                {
                    Integer cnt=tag_count.get(preBuildTags.get(tag)).get(0);
                    tag_count.get(preBuildTags.get(tag)).set(0,cnt+1);
                }
                else
                {
                    Integer cnt=tag_count.get(preBuildTags.get(tag)).get(1);
                    tag_count.get(preBuildTags.get(tag)).set(1,cnt+1);
                }
            }
        }

        String temp="dp - solved : "+tag_count.get(preBuildTags.get("dp")).get(0)+" wrong_ans : "+tag_count.get(preBuildTags.get("dp")).get(1);
        
        return ResponseEntity.ok().body(temp);
    }

}

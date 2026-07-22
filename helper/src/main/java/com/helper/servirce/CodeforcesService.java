package com.helper.servirce;

import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.helper.Exception.GeneratingException.CodeforcesHandlerNotExist;
import com.helper.Exception.GeneratingException.EmailNotFoundException;
import com.helper.dto.codeforcesDto.CodeforcesProblemInfo;
import com.helper.dto.codeforcesDto.CodeforcesResponse;
import com.helper.dto.codeforcesDto.CodeforcesSubmissionInfo;
import com.helper.dto.codeforcesDto.CodeforcesUserStatusInfo;
import com.helper.repository.CodeforcesRepository;

import java.util.HashMap;

import org.modelmapper.ModelMapper;

import com.helper.entity.Users;

import com.helper.entity.Codeforces;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication; 
import org.springframework.security.core.userdetails.UserDetails;

import com.helper.entity.Tag;
import com.helper.entity.TagStatistic;
import com.helper.repository.TagRepository;
import com.helper.repository.TagStatisticRepository;
import com.helper.repository.UserRepository;

@Service
public class CodeforcesService {

    private final RestTemplate restTemplate;
    private final CodeforcesRepository codeforcesRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private TagStatisticRepository tagStatisticRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    public CodeforcesService(RestTemplate restTemplate,CodeforcesRepository codeforcesRepository,ModelMapper modelMapper)
    {
        this.restTemplate=restTemplate;
        this.codeforcesRepository=codeforcesRepository;
        this.modelMapper=modelMapper;
    }



    public CodeforcesResponse getCodeforcesUserInfo(String handle)
    {
        String url = "https://codeforces.com/api/user.info?handles={handle}";

        return restTemplate.getForObject(
                                    url,
                                    CodeforcesResponse.class,
                                    handle
                                );
    }

    public CodeforcesUserStatusInfo getCodeforcesUserStatusInfo(String handle)
    {
        String url = "https://codeforces.com/api/user.status?handle={handle}";

        return restTemplate.getForObject(
                                        url,
                                        CodeforcesUserStatusInfo.class,
                                        handle
        );
    }

    public String reloadUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail=(UserDetails)authentication.getPrincipal();
        String username=userDetail.getUsername();
        
        Users user=(Users)userRepository.findByEmail(username)
                                            .orElse(null);
        Codeforces codeforces=(Codeforces)codeforcesRepository.findByEmail(username).orElse(null);
        
        
        if(user == null)
            {
                throw new EmailNotFoundException("User not found inside the codeforces service");
        }
        if(codeforces==null)
            {
                throw new CodeforcesHandlerNotExist("codeforces handler not found inside the codeforces service.");
            }
        CodeforcesUserStatusInfo codeforcesUserStatusInfo=getCodeforcesUserStatusInfo(codeforces.getHandle());

        List<CodeforcesSubmissionInfo> codeforcesSubmissionInfos=codeforcesUserStatusInfo.getResult();

        Integer n=codeforcesSubmissionInfos.size();

        List<Tag> tags=tagRepository.findAll();

        Map<String,Tag> preBuildTags=tags.stream()
                                        .collect(Collectors.toMap(Tag::getName,Function.identity()));

        Map<String,Integer> solved_submission=new HashMap<>();
        Map<String,Integer> wrong_submission=new HashMap<>();

        for(CodeforcesSubmissionInfo cf_info : codeforcesSubmissionInfos)
        {
            boolean isSolved=cf_info.getStatus().equals("OK")?true:false;
            CodeforcesProblemInfo codeforcesProblemInfo=cf_info.getProblem();
            List<String> submission_tag=codeforcesProblemInfo.getTags();
            for(String t:submission_tag)
            {
                if(preBuildTags.get(t)==null)
                    continue;
                if(isSolved)
                {
                    Integer cnt=solved_submission.getOrDefault(preBuildTags.get(t).getName(),0);
                    solved_submission.put(preBuildTags.get(t).getName(),cnt+1);
                }
                else
                {
                    Integer cnt=wrong_submission.getOrDefault(preBuildTags.get(t).getName(),0);
                    wrong_submission.put(preBuildTags.get(t).getName(),cnt+1);
                }
            }
        }

        for(Map.Entry<String,Tag> it : preBuildTags.entrySet())
        {
            String submission_tag=it.getKey();
            Tag tag=it.getValue();

            Integer cnt_solved=solved_submission.get(submission_tag);
            Integer cnt_wrong=wrong_submission.get(submission_tag);

            TagStatistic stats=new TagStatistic(cnt_solved,tag,codeforces,cnt_wrong);

            tagStatisticRepository.save(stats);

        }
        
        return "user reloaded";
    }

    public List<List<Tag>> getWeekness()
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail=(UserDetails)authentication.getPrincipal();
        String username=userDetail.getUsername();

        Codeforces codeforces=(Codeforces)codeforcesRepository.findByEmail(username).orElse(null);

        if(codeforces==null)
            throw new CodeforcesHandlerNotExist("codeforces handler not found inside the codeforces service.");
        
        List<TagStatistic> stats=tagStatisticRepository.findByCodeforces(codeforces);

        PriorityQueue<TagStatistic> pq=new PriorityQueue<>(
                Comparator.comparing(ts -> ts.getTag().getId())
        );

        pq.addAll(stats);

        List<List<Tag>> weekness=new ArrayList<>();

        for(int i=0;i<3;i++)
            weekness.add(new ArrayList<>());

        while(!pq.isEmpty())
        {
            TagStatistic curr=pq.poll();

            Integer solved=curr.getSolvedProblemCount();
            Integer wrong=curr.getWrongAnswerCount();

            if(solved==null)
                solved=0;
            if(wrong==null)
                wrong=0;

            if(solved<10)
            {
                weekness.get(0).add(curr.getTag());
            }
            else if(solved<31 && wrong>=solved)
            {
                weekness.get(0).add(curr.getTag());
            }
            else if(solved<=31 && solved>=10)
            {
                weekness.get(1).add(curr.getTag());
            }
            else
            {
                weekness.get(2).add(curr.getTag());
            }
        }
        return weekness;
    }

}
 
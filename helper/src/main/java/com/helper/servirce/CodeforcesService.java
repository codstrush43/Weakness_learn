package com.helper.servirce;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.helper.dto.codeforcesDto.CodeforcesResponse;
import com.helper.dto.codeforcesDto.CodeforcesUserStatusInfo;
import com.helper.repository.CodeforcesRepository;

import org.modelmapper.ModelMapper;
import com.helper.entity.Users;
import com.helper.dto.codeforcesDto.CodeforcesUserInfo;
import com.helper.dto.signup.SignupRequest;
import com.helper.entity.Codeforces;

@Service
public class CodeforcesService {

    private final RestTemplate restTemplate;
    private final CodeforcesRepository codeforcesRepository;
    private final ModelMapper modelMapper;

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


}
 
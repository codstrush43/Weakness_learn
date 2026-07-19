package com.helper.servirce;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.helper.dto.codeforcesDto.CodeforcesResponse;

@Service
public class CodeforcesService {

    private final RestTemplate restTemplate;

    CodeforcesService(RestTemplate restTemplate)
    {
        this.restTemplate=restTemplate;
    }

    public CodeforcesResponse getCodeforcesResponse(String handle)
    {
        String url = "https://codeforces.com/api/user.info?handles={handle}";

        return restTemplate.getForObject(
                                    url,
                                    CodeforcesResponse.class,
                                    handle
                                );
    }

}
 
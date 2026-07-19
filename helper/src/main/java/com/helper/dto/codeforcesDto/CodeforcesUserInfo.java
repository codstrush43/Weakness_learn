package com.helper.dto.codeforcesDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeforcesUserInfo {

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("rank")
    private String rank;

    @JsonProperty("maxRating")
    private Integer maxRating;

    @JsonProperty("maxRank")
    private String maxRank;

    @JsonProperty("email")
    private String email;
}

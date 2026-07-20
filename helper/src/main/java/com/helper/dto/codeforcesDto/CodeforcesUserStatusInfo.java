package com.helper.dto.codeforcesDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class CodeforcesUserStatusInfo {
    @JsonProperty("status")
    private String status;
    @JsonProperty("result")
    private List<CodeforcesSubmissionInfo> result;
}

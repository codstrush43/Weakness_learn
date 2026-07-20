package com.helper.dto.codeforcesDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class CodeforcesSubmissionInfo {
    @JsonProperty("id")
    private Integer submission_id;

    @JsonProperty("problem")
    private CodeforcesProblemInfo problem;

    @JsonProperty("verdict")
    private String status;
}

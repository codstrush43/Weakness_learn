package com.helper.dto.codeforcesDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  
@NoArgsConstructor
public class CodeforcesProblemInfo {
    @JsonProperty("tags")
    private List<String> tags;
}




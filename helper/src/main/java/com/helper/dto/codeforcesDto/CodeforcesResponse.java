package com.helper.dto.codeforcesDto;

import java.util.List;
import com.helper.dto.codeforcesDto.CodeforcesUserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeforcesResponse {

    private String status;
    private List<CodeforcesUserInfo> result;
}

package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class JudgeRoundStatDTO {
    private String firstName;
    private String lastName;
    private Long judgeId;
    private Integer roundsJudged;

    public JudgeRoundStatDTO(String firstName, String lastName, Long judgeId, Integer roundsJudged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.judgeId = judgeId;
        this.roundsJudged = roundsJudged;
    }
}

package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class JudgeScoresDTO {
    private String firstName;
    private String lastName;
    private List<Float> scores;

    public JudgeScoresDTO(String firstName, String lastName, List<Float> scores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.scores = scores;
    }
}

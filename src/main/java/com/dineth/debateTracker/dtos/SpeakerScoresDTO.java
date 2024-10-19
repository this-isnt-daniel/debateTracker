package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @Getter @Setter
public class SpeakerScoresDTO {
    private String firstName;
    private String lastName;
    private List<Float> scores;
    private Double avgScore;
    private Long roundsDebated;

    public SpeakerScoresDTO(String firstName, String lastName, List<Float> scores, Double avgScore, Long roundsDebated){
        this.firstName=firstName;
        this.lastName=lastName;
        this.scores=scores;
        this.avgScore=avgScore;
        this.roundsDebated=roundsDebated;
    }
}

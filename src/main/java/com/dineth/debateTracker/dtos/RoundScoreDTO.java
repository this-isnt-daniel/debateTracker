package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RoundScoreDTO {
    private String roundName;
    private Long roundId;
    private Double score;
    private Integer speakerPosition;

    public RoundScoreDTO(String roundName, Long roundId, Double score, Integer speakerPosition) {
        this.roundName = roundName;
        this.roundId = roundId;
        this.score = score;
        this.speakerPosition = speakerPosition;
    }
}

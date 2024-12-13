package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpeakerScoreDTO {
    private Long tournamentId;
    private String tournamentName;
    private Long roundId;
    private String roundName;
    private double score;

    public SpeakerScoreDTO(Long tournamentId, String tournamentName, Long roundId, String roundName, double score) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.roundId = roundId;
        this.roundName = roundName;
        this.score = score;
    }

    @Override
    public String toString() {
        return "SpeakerScoreDTO{" +
                "tournamentId=" + tournamentId +
                ", tournamentName='" + tournamentName + '\'' +
                ", roundId=" + roundId +
                ", roundName='" + roundName + '\'' +
                ", score=" + score +
                '}';
    }

}

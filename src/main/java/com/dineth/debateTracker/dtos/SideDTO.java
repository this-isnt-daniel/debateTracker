package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SideDTO {
    private String teamId;
    private List<FinalTeamBallotDTO> ballots;
    private List<SpeechDTO> speeches;

    public SideDTO(String teamId, List<FinalTeamBallotDTO> ballots, List<SpeechDTO> speeches) {
        this.teamId = teamId;
        this.ballots = ballots;
        this.speeches = speeches;
    }

    @Override
    public String toString() {
        return "SideDTO{" +
                "teamId='" + teamId + '\'' +
                ", ballots=" + ballots +
                ", speeches=" + speeches +
                '}';
    }

}

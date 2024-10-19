package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class SpeakerTournamentDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String teamName;
    private List<Float> scores;
    private String tournamentShortName;
    private Integer roundNo;

    public SpeakerTournamentDTO(String firstName, String lastName, String phone, List<Float> scores, String tournamentShortName, Integer roundNo, String teamName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.phone = phone;
        this.scores = scores;
        this.tournamentShortName = tournamentShortName;
        this.roundNo = roundNo;
    }
}

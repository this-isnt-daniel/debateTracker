package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class JudgeTournamentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String tournamentShortName;
    private String phone;
    private List<String> rounds;

    public JudgeTournamentDTO(Long id, String firstName, String lastName, String phone, List<String> rounds, String tournamentShortName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.rounds = rounds;
        this.tournamentShortName = tournamentShortName;
    }
}

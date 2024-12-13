package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Base class for all person tournament score DTOs
 */
@Getter @Setter @NoArgsConstructor
public abstract class PersonTournamentScoreDTO {
    private String firstName;
    private String lastName;
    private Long id;
    private List<TournamentRoundDTO> tournamentRoundScores;

    public PersonTournamentScoreDTO(String firstName, String lastName, Long id, List<TournamentRoundDTO> tournamentRoundScores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.tournamentRoundScores = tournamentRoundScores;
    }

}


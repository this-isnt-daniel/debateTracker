package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class DebaterTournamentScoreDTO extends PersonTournamentScoreDTO {
    private int totalDebatesParticipated;

    public DebaterTournamentScoreDTO(String firstName, String lastName, Long id,
                                     List<TournamentRoundDTO> tournamentRoundScores) {
        super(firstName, lastName, id,tournamentRoundScores);
        if (tournamentRoundScores != null)
            this.totalDebatesParticipated = tournamentRoundScores.stream().mapToInt(TournamentRoundDTO::getNumberOfRounds).sum();
    }
    public void addTournamentRoundScore(TournamentRoundDTO tournamentRoundScore) {
        if (this.getTournamentRoundScores() == null) {
            this.setTournamentRoundScores(new ArrayList<>());
        }
        this.getTournamentRoundScores().add(tournamentRoundScore);
        this.totalDebatesParticipated += tournamentRoundScore.getNumberOfRounds();
    }
    public void setTournamentRoundScores(List<TournamentRoundDTO> tournamentRoundScores) {
        if (tournamentRoundScores != null)
            this.totalDebatesParticipated = tournamentRoundScores.stream().mapToInt(TournamentRoundDTO::getNumberOfRounds).sum();
        super.setTournamentRoundScores(tournamentRoundScores);
    }

}
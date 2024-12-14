package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class JudgeTournamentScoreDTO extends PersonTournamentScoreDTO {
    private int totalDebatesJudged;

    public JudgeTournamentScoreDTO(String firstName, String lastName, Long id,
                                   List<TournamentRoundDTO> tournamentRoundScores) {
        super(firstName, lastName, id,tournamentRoundScores);
        if (tournamentRoundScores != null)
            this.totalDebatesJudged = tournamentRoundScores.stream().mapToInt(TournamentRoundDTO::getNumberOfRounds).sum();
    }
    public void addTournamentRoundScore(TournamentRoundDTO tournamentRoundScore) {
        if (this.getTournamentRoundScores() == null) {
            this.setTournamentRoundScores(new ArrayList<>());
        }
        this.getTournamentRoundScores().add(tournamentRoundScore);
        this.totalDebatesJudged += tournamentRoundScore.getNumberOfRounds();
    }
    public void setTournamentRoundScores(List<TournamentRoundDTO> tournamentRoundScores) {
        if (tournamentRoundScores != null)
            this.totalDebatesJudged = tournamentRoundScores.stream().mapToInt(TournamentRoundDTO::getNumberOfRounds).sum();
        super.setTournamentRoundScores(tournamentRoundScores);
    }

}
package com.dineth.debateTracker.tournament;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getTournament() {
        return tournamentRepository.findAll();
    }

    public Tournament addTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }


}

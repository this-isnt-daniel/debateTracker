package com.dineth.debateTracker.eliminationballot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EliminationBallotService {
    private final EliminationBallotRepository eliminationBallotRepository;

    @Autowired
    public EliminationBallotService(EliminationBallotRepository eliminationBallotRepository) {
        this.eliminationBallotRepository = eliminationBallotRepository;
    }

    public List<EliminationBallot> getEliminationBallots() {
        return eliminationBallotRepository.findAll();
    }

    public EliminationBallot addEliminationBallot(EliminationBallot eliminationBallot) {
        return eliminationBallotRepository.save(eliminationBallot);
    }

    public EliminationBallot findEliminationBallotById(Long id) {
        return eliminationBallotRepository.findById(id).orElse(null);
    }

}

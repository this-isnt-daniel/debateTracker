package com.dineth.debateTracker.round;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoundService {
    private final RoundRepository roundRepository;

    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    public List<Round> getRound() {
        return roundRepository.findAll();
    }

    public Round addRound(Round round) {
        return roundRepository.save(round);
    }


}

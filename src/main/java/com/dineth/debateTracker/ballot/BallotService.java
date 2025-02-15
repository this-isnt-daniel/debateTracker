package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.debater.Debater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BallotService {
    private final BallotRepository ballotRepository;

    @Autowired
    public BallotService(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    public List<Ballot> getBallots() {
        return ballotRepository.findAll();
    }

    public Ballot addBallot(Ballot ballot) {
        return ballotRepository.save(ballot);
    }

    public List<Ballot> findBallotsByDebater(Debater debater) {
        return ballotRepository.findBallotsByDebater(debater);
    }

    @Transactional
    public void replaceDebater(Debater oldDebater, Debater newDebater) {
        List<Ballot> ballots = findBallotsByDebater(oldDebater);
        for (Ballot ballot : ballots) {
            ballot.setDebater(newDebater);
            ballotRepository.save(ballot);
        }
    }

}

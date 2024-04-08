package com.dineth.debateTracker.ballot;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BallotService {
    private final BallotRepository ballotRepository;

    public BallotService(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    public List<Ballot> getBallots() {
        return ballotRepository.findAll();
    }

    public Ballot addBallot(Ballot ballot) {
        return ballotRepository.save(ballot);
    }


}

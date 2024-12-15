package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.debater.Debater;
import com.dineth.debateTracker.dtos.JudgeScoresDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BallotService {
    private final BallotRepository ballotRepository;

    @Autowired
    public BallotService(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
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


    public List<JudgeScoresDTO> getJudgeScoresOverall() {
        List<Object[]> results = ballotRepository.getRankedJudgesBySpeaks();
        List<JudgeScoresDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            String firstName = (String) result[0];
            String lastName = (String) result[1];

            // Convert array to List
            Float[] scoresArray = (Float[]) result[2]; // Assuming speaker_score is stored as a Float
            List<Float> scores = Arrays.asList(scoresArray); // Convert the array to a List

            // Create DTO including team name
            JudgeScoresDTO dto = new JudgeScoresDTO(firstName, lastName, scores);
            dtos.add(dto);
        }
        return dtos;
    }
}

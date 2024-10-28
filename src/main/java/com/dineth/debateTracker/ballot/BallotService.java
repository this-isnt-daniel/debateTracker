package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.debater.Debater;
import com.dineth.debateTracker.dtos.JudgeScoresDTO;
import com.dineth.debateTracker.dtos.SpeakerScoresDTO;
import com.dineth.debateTracker.dtos.SpeakerTournamentDTO;
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

    public List<Ballot> getBallots() {
        return ballotRepository.findAll();
    }

    public Ballot addBallot(Ballot ballot) {
        return ballotRepository.save(ballot);
    }

    public Ballot findBallotById(Long id) {
        return ballotRepository.findById(id).orElse(null);
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

    /**
     * Find all ballots by debater that aren't replies
     **/
    public List<Ballot> findBallotsByDebaterAndIsSubstantive(Debater debater) {
        return ballotRepository.findBallotsByDebaterAndSpeakerScoreGreaterThan(debater, 68.0F);
    }

    /**
     * Get names and scores of each debater at each tournament
     */
    public List<SpeakerTournamentDTO> getDebaterScores() {
        List<Object[]> results = ballotRepository.findDebaterScoresNative();
        List<SpeakerTournamentDTO> dtos = new ArrayList<>();

        for (Object[] result : results) {
            String firstName = (String) result[0];
            String lastName = (String) result[1];
            String phone = (String) result[2];

            // Convert array to List
            Float[] scoresArray = (Float[]) result[3]; // Assuming speaker_score is stored as a Float
            List<Float> scores = Arrays.asList(scoresArray); // Convert the array to a List

            String tournamentShortName = (String) result[4];
            Integer roundNo = (Integer) result[5];
            String teamName = (String) result[6]; // Retrieve team name from result

            // Create DTO including team name
            SpeakerTournamentDTO dto = new SpeakerTournamentDTO(firstName, lastName, phone, scores, tournamentShortName, roundNo, teamName);
            dtos.add(dto);
        }

        return dtos;
    }


    /**
     * Get names and all the scores of each debater at tournaments combined
     */
    public List<SpeakerScoresDTO> getDebaterScoresII() {
        List<Object[]> results = ballotRepository.getRankedDebaters();
        List<SpeakerScoresDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            String firstName = (String) result[0];
            String lastName = (String) result[1];

            // Convert array to List
            Float[] scoresArray = (Float[]) result[2]; // Assuming speaker_score is stored as a Float
            List<Float> scores = Arrays.asList(scoresArray); // Convert the array to a List

            Double avgScore = (Double) result[3];

            Long roundsDebated = (Long) result[4];

            // Create DTO including team name
            SpeakerScoresDTO dto = new SpeakerScoresDTO(firstName, lastName, scores, avgScore, roundsDebated);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Get all the scores of a debater at tournaments combined
     */
    public SpeakerScoresDTO getDebaterScores(String fname,String lname) {
        List<Object[]> results = ballotRepository.getRankedDebaters(fname,lname);
        if (results.isEmpty()) {
            return null;
        }
        Object[] result = results.get(0);
        String firstName = (String) result[0];
        String lastName = (String) result[1];

        // Convert array to List
        Float[] scoresArray = (Float[]) result[2]; // Assuming speaker_score is stored as a Float
        List<Float> scores = Arrays.asList(scoresArray); // Convert the array to a List

        Double avgScore = (Double) result[3];

        Long roundsDebated = (Long) result[4];

        // Create DTO including team name

        return new SpeakerScoresDTO(firstName, lastName, scores, avgScore, roundsDebated);
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

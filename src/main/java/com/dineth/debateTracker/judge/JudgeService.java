package com.dineth.debateTracker.judge;

import com.dineth.debateTracker.dtos.JudgeTournamentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JudgeService {
    private final JudgeRepository judgeRepository;

    @Autowired
    public JudgeService(JudgeRepository judgeRepository) {
        this.judgeRepository = judgeRepository;
    }

    public List<Judge> getJudges() {
        return judgeRepository.findAll();
    }

    public Judge findJudgeById(Long id) {
        return judgeRepository.findById(id).orElse(null);
    }
    public Judge addJudge(Judge judge) {
        return judgeRepository.save(judge);
    }

    public Judge checkJudgeExists(Judge judge) {
        return judgeRepository.findByFnameAndLname(judge.getFname(), judge.getLname());
    }

    public List<JudgeTournamentDTO> getJudgesByTournament() {
        List<Object[]> temp = judgeRepository.getJudgesByTournament();
        List<JudgeTournamentDTO> result = new ArrayList<>();
        for (Object[] obj : temp) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] roundArray = (String[]) obj[2];  // Cast to String[]
            List<String> rounds = Arrays.asList(roundArray);  // Convert to List<String>
            String tournamentShortName = (String) obj[3];

            JudgeTournamentDTO judgeTournamentDTO = new JudgeTournamentDTO(firstName, lastName, "", rounds, tournamentShortName);
            result.add(judgeTournamentDTO);
        }
        return result;
    }


}

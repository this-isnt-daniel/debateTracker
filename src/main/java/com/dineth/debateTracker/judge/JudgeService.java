package com.dineth.debateTracker.judge;

import com.dineth.debateTracker.dtos.JudgeTournamentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<JudgeTournamentDTO> getJudgesByTournamentWithRounds() {
        List<Object[]> tempPrelims = judgeRepository.getJudgesByTournamentWithPrelims();
        List<Object[]> tempBreaks = judgeRepository.getJudgesByTournamentWithBreaks();

        HashMap<String, JudgeTournamentDTO> map = new HashMap<>();

        // Process prelim rounds
        for (Object[] obj : tempPrelims) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] roundArray = (String[]) obj[2];
            List<String> rounds = new ArrayList<>(Arrays.asList(roundArray));
            String tournamentShortName = (String) obj[3];

            String key = firstName + " " + lastName + " " + tournamentShortName;
            JudgeTournamentDTO judgeTournamentDTO = new JudgeTournamentDTO(firstName, lastName, "", rounds, tournamentShortName);
            map.put(key, judgeTournamentDTO);
        }

        // Process break rounds
        for (Object[] obj : tempBreaks) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] roundArray = (String[]) obj[2];
            List<String> rounds = Arrays.asList(roundArray);
            String tournamentShortName = (String) obj[3];

            String key = firstName + " " + lastName + " " + tournamentShortName;
            JudgeTournamentDTO judgeTournamentDTO = map.get(key);

            if (judgeTournamentDTO != null) {
                judgeTournamentDTO.getRounds().addAll(rounds);  // Merge rounds if exists
            } else {
                judgeTournamentDTO = new JudgeTournamentDTO(firstName, lastName, "", rounds, tournamentShortName);
                map.put(key, judgeTournamentDTO);  // Add to map if new
            }
        }

        return new ArrayList<>(map.values());
    }

    public List<JudgeTournamentDTO> getJudgesByTournament() {
        List<Object[]> tempPrelims = judgeRepository.getJudgesByTournamentPrelims();
        List<Object[]> tempBreaks = judgeRepository.getJudgesByTournamentBreaks();

        HashMap<String, JudgeTournamentDTO> map = new HashMap<>();

        // Process prelim rounds
        for (Object[] obj : tempPrelims) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] tournamentArray = (String[]) obj[2];  // Cast to String[]
            List<String> tournaments = Arrays.asList(tournamentArray);  // Convert to List<String>

            JudgeTournamentDTO judgeTournamentDTO = new JudgeTournamentDTO(firstName, lastName, "", tournaments, "");
            String key = firstName + " " + lastName;
            map.put(key, judgeTournamentDTO);
        }
        // Process break rounds
        for (Object[] obj : tempBreaks) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] tournamentArray = (String[]) obj[2];  // Cast to String[]
            List<String> tournaments = Arrays.asList(tournamentArray);  // Convert to List<String>

            JudgeTournamentDTO judgeTournamentDTO = map.get(firstName + " " + lastName);
            if (judgeTournamentDTO != null) {
                //merge tournaments if exists and get distinct values
                Set<String> set = new HashSet<>(judgeTournamentDTO.getRounds());
                set.addAll(tournaments);
                judgeTournamentDTO.setRounds(new ArrayList<>(set));
                map.put(firstName + " " + lastName, judgeTournamentDTO);  // Add to map if exists
            } else {
                judgeTournamentDTO = new JudgeTournamentDTO(firstName, lastName, "", tournaments, "");
                map.put(firstName + " " + lastName, judgeTournamentDTO);  // Add to map if new
            }
        }
        return new ArrayList<>(map.values());
    }


    public List<HashMap<String,Integer>> getRoundCount() {
        HashMap<String, Integer> map = new HashMap<>();
        List<JudgeTournamentDTO> temp = getJudgesByTournamentWithRounds();
        for (JudgeTournamentDTO judgeTournamentDTO : temp) {
            String key = judgeTournamentDTO.getFirstName() + " " + judgeTournamentDTO.getLastName();
            map.merge(key, judgeTournamentDTO.getRounds().size(), Integer::sum);
        }
        return Collections.singletonList(map);
    }
}

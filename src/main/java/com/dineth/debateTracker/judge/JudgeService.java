package com.dineth.debateTracker.judge;

import com.dineth.debateTracker.dtos.JudgeRoundStatDTO;
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
            Long judgeId = (Long) obj[4];

            String key = judgeId + " " + tournamentShortName;
            JudgeTournamentDTO judgeTournamentDTO = new JudgeTournamentDTO(judgeId,firstName, lastName, "", rounds, tournamentShortName);
            map.put(key, judgeTournamentDTO);
        }

        // Process break rounds
        for (Object[] obj : tempBreaks) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] roundArray = (String[]) obj[2];
            List<String> rounds = Arrays.asList(roundArray);
            String tournamentShortName = (String) obj[3];
            Long judgeId = (Long) obj[4];

            String key = judgeId + " " + tournamentShortName;
            JudgeTournamentDTO judgeTournamentDTO = map.get(key);

            if (judgeTournamentDTO != null) {
                judgeTournamentDTO.getRounds().addAll(rounds);  // Merge rounds if exists
            } else {
                judgeTournamentDTO = new JudgeTournamentDTO(judgeId,firstName, lastName, "", rounds, tournamentShortName);
                map.put(key, judgeTournamentDTO);  // Add to map if new
            }
        }

        return new ArrayList<>(map.values());
    }

    public List<JudgeTournamentDTO> getJudgesByTournament() {
        List<Object[]> tempPrelims = judgeRepository.getJudgesByTournamentPrelims();
        List<Object[]> tempBreaks = judgeRepository.getJudgesByTournamentBreaks();

        HashMap<Long, JudgeTournamentDTO> map = new HashMap<>();

        // Process prelim rounds
        for (Object[] obj : tempPrelims) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] tournamentArray = (String[]) obj[2];  // Cast to String[]
            List<String> tournaments = Arrays.asList(tournamentArray);  // Convert to List<String>
            Long judgeId = (Long) obj[3];

            JudgeTournamentDTO judgeTournamentDTO = new JudgeTournamentDTO(judgeId,firstName, lastName, "", tournaments, "");
            map.put(judgeId, judgeTournamentDTO);
        }
        // Process break rounds
        for (Object[] obj : tempBreaks) {
            String firstName = (String) obj[0];
            String lastName = (String) obj[1];
            String[] tournamentArray = (String[]) obj[2];  // Cast to String[]
            List<String> tournaments = Arrays.asList(tournamentArray);  // Convert to List<String>
            Long judgeId = (Long) obj[3];

            JudgeTournamentDTO judgeTournamentDTO = map.get(judgeId);
            if (judgeTournamentDTO != null) {
                //merge tournaments if exists and get distinct values
                Set<String> set = new HashSet<>(judgeTournamentDTO.getRounds());
                set.addAll(tournaments);
                judgeTournamentDTO.setRounds(new ArrayList<>(set));
                map.put(judgeId, judgeTournamentDTO);  // Add to map if exists
            } else {
                judgeTournamentDTO = new JudgeTournamentDTO(judgeId,firstName, lastName, "", tournaments, "");
                map.put(judgeId, judgeTournamentDTO);  // Add to map if new
            }
        }
        return new ArrayList<>(map.values());
    }


    public List<JudgeRoundStatDTO> getRoundCount() {
        HashMap<Long, JudgeRoundStatDTO> map = new HashMap<>();
        List<JudgeTournamentDTO> temp = getJudgesByTournamentWithRounds();
        for (JudgeTournamentDTO judgeTournamentDTO : temp) {
            Long key = judgeTournamentDTO.getId();
            Integer value = judgeTournamentDTO.getRounds().size();
            if (map.containsKey(key)) {
                map.put(key, new JudgeRoundStatDTO(judgeTournamentDTO.getFirstName(), judgeTournamentDTO.getLastName(), judgeTournamentDTO.getId(), map.get(key).getRoundsJudged() + value));
            } else {
                map.put(key, new JudgeRoundStatDTO(judgeTournamentDTO.getFirstName(), judgeTournamentDTO.getLastName(), judgeTournamentDTO.getId(), value));
            }
        }
        return  new ArrayList<>(map.values());
    }

//    },
//    {
//        "id": 149,
//        "firstName": "Ahmedh",
//        "lastName": "Moulana",
//        "tournamentShortName": "SLSDC 2024 E",
//        "phone": "",
//        "rounds": [
//            "Round 1"
//        ]
//    },
//    {
//        "id": 31,
//        "firstName": "Rusira",
//        "lastName": "Ekanayake",
//        "tournamentShortName": "NMPs 2024",
//        "phone": "",
//        "rounds": [
//            "Round 2",
//            "Round 3",
//            "Round 4",
//            "Round 5",
//            "Quarterfinals"
//        ]
//    },
//    {
}

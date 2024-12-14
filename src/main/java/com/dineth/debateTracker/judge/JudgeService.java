package com.dineth.debateTracker.judge;

import com.dineth.debateTracker.dtos.JudgeTournamentDTO;
import com.dineth.debateTracker.dtos.JudgeTournamentScoreDTO;
import com.dineth.debateTracker.dtos.RoundScoreDTO;
import com.dineth.debateTracker.dtos.TournamentRoundDTO;
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

    /**
     * get the number of rounds judged by a judge
     */
    public HashMap<String,Integer> getRoundsJudged(Long judgeID) {
        JudgeTournamentScoreDTO breaks = this.getTournamentsAndBreaksJudged(judgeID);
        JudgeTournamentScoreDTO prelims = this.getTournamentsAndScoresForJudge(judgeID, false);
        return new HashMap<>() {{
            put("prelims", prelims.getTotalDebatesJudged());
            put("breaks", breaks.getTotalDebatesJudged());
        }};
    }

    /**
     * get all speaks given by a judge at each tournament
     */
    public JudgeTournamentScoreDTO getTournamentsAndScoresForJudge(Long judgeID,Boolean reply) {
        List<Object> temp = judgeRepository.findTournamentsAndScoresForJudge(judgeID);
        Judge judge = findJudgeById(judgeID);
        JudgeTournamentScoreDTO x = new JudgeTournamentScoreDTO(judge.getFname(), judge.getLname(), judge.getId(), null);

        HashMap<Long, TournamentRoundDTO> tournamentMap = new HashMap<>();

        for (Object o : temp) {
            Object[] obj = (Object[]) o;
            Long tid = (Long) obj[0];
            if (!tournamentMap.containsKey(tid)) {
                TournamentRoundDTO tr = new TournamentRoundDTO((String) obj[1], tid, null);
                tournamentMap.put(tid, tr);
            }
        }

        for (Object o : temp) {
            Object[] obj = (Object[]) o;
            Long tid = (Long) obj[0];
            TournamentRoundDTO tr = tournamentMap.get(tid);
            RoundScoreDTO rs = new RoundScoreDTO((String) obj[3], (Long) obj[2], ((Float) obj[4]).doubleValue(), (Integer) obj[5]);
//            skip reply rounds if required
            if (!reply && rs.getSpeakerPosition() == 4) continue;
            tr.addRoundScore(rs);
            tournamentMap.put(tid, tr);
        }
        x.setTournamentRoundScores(new ArrayList<>(tournamentMap.values()));
        return x;
    }

    /**
     * get all break rounds judged by a judge at each tournament
     */

    public JudgeTournamentScoreDTO getTournamentsAndBreaksJudged(Long judgeID) {
        List<Object> temp = judgeRepository.findTournamentsAndBreaksJudged(judgeID);
        Judge judge = findJudgeById(judgeID);
        JudgeTournamentScoreDTO x = new JudgeTournamentScoreDTO(judge.getFname(), judge.getLname(), judge.getId(), null);

        HashMap<Long, TournamentRoundDTO> tournamentMap = new HashMap<>();

        for (Object o : temp) {
            Object[] obj = (Object[]) o;
            Long tid = (Long) obj[0];
            if (!tournamentMap.containsKey(tid)) {
                TournamentRoundDTO tr = new TournamentRoundDTO((String) obj[1], tid, null);
                tournamentMap.put(tid, tr);
            }
        }

        for (Object o : temp) {
            Object[] obj = (Object[]) o;
            Long tid = (Long) obj[0];
            TournamentRoundDTO tr = tournamentMap.get(tid);
            RoundScoreDTO rs = new RoundScoreDTO((String) obj[3], (Long) obj[2], 0.0, 5);
            tr.addRoundScore(rs);
            tournamentMap.put(tid, tr);
        }
        x.setTournamentRoundScores(new ArrayList<>(tournamentMap.values()));
        return x;
    }
}

package com.dineth.debateTracker.judge;

import com.dineth.debateTracker.dtos.JudgeTournamentScoreDTO;
import com.dineth.debateTracker.dtos.RoundScoreDTO;
import com.dineth.debateTracker.dtos.TournamentRoundDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    /**
     * get all the tournaments judged by a given judge
     */
    public List<String> getJudgesByTournament(Long judgeID) {
        JudgeTournamentScoreDTO breaks = this.getTournamentsAndBreaksJudged(judgeID);
        JudgeTournamentScoreDTO prelims = this.getTournamentsAndScoresForJudge(judgeID, false);

        HashSet<String> set = new HashSet<>();
        for (TournamentRoundDTO tr : breaks.getTournamentRoundScores()) {
            set.add(tr.getTournamentShortName());
        }
        for (TournamentRoundDTO tr : prelims.getTournamentRoundScores()) {
            set.add(tr.getTournamentShortName());
        }
        return new ArrayList<>(set);


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

package com.dineth.debateTracker.judge;

import com.dineth.debateTracker.dtos.JudgeTournamentDTO;
import com.dineth.debateTracker.dtos.JudgeTournamentScoreDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController @Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/judge")
public class JudgeController {
    private final JudgeService judgeService;

    @Autowired
    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @GetMapping
    public List<Judge> getJudges() {
        return judgeService.getJudges();
    }

    /**
     * Get all judges and the rounds they've judged in each tournament
     */
    @GetMapping(path = "tournament-rounds")
    public List<JudgeTournamentDTO> getJudgesByTournamentWithRounds() {
        return judgeService.getJudgesByTournamentWithRounds();
    }

    /**
     * Get all judges and the tournaments they've judged at
     */
    @GetMapping(path = "tournaments")
    public List<JudgeTournamentDTO> getJudgesByTournament() {
        return judgeService.getJudgesByTournament();
    }
    @GetMapping(path = "rounds/count/{id}")
    public HashMap<String, Integer> getRoundsJudged(@PathVariable Long id) {
        return judgeService.getRoundsJudged(id);
    }

    @PostMapping
    public Judge addJudge(@RequestBody Judge judge) {
        return judgeService.addJudge(judge);
    }

    /**
     * For a judge get the tournaments they've judged prelims at along with scores given
     */
    @GetMapping(path = "rounds/prelims/{id}")
    public JudgeTournamentScoreDTO getPrelimScoresByJudge(@PathVariable Long id,
                                                          @RequestParam(value = "reply", required = false, defaultValue = "false") Boolean reply) {
        return judgeService.getTournamentsAndScoresForJudge(id,reply);
    }
    /**
     * For a judge get the tournaments they've judged break rounds at
     */
    @GetMapping(path = "rounds/breaks/{id}")
    public JudgeTournamentScoreDTO getBreakScoresByJudge(@PathVariable Long id) {
        return judgeService.getTournamentsAndBreaksJudged(id);
    }

}

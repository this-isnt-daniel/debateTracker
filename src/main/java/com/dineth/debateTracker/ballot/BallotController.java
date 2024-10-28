package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.dtos.JudgeScoresDTO;
import com.dineth.debateTracker.dtos.SpeakerScoresDTO;
import com.dineth.debateTracker.dtos.SpeakerTournamentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/ballot")
public class BallotController {

    private final BallotService ballotService;

    @Autowired
    public BallotController(BallotService ballotService) {
        this.ballotService = ballotService;
    }

    @GetMapping("debater/tournament-scores")
    public List<SpeakerTournamentDTO> getDebaterScoresPerTournament() {
        return ballotService.getDebaterScores();
    }
    @GetMapping("debater/scores-all")
    public List<SpeakerScoresDTO> getDebaterScoresOverall() {
        return ballotService.getDebaterScoresII();
    }
    @GetMapping("debater/scores")
    public SpeakerScoresDTO getDebaterScoresByName(@RequestParam  String fname, @RequestParam String lname) {
        return ballotService.getDebaterScores(fname, lname);
    }
    @GetMapping("judge/scores-all")
    public List<JudgeScoresDTO> getJudgeScoresOverall() {
        return ballotService.getJudgeScoresOverall();
    }

}

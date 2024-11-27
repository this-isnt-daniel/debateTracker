package com.dineth.debateTracker.debater;

import com.dineth.debateTracker.ballot.Ballot;
import com.dineth.debateTracker.ballot.BallotService;
import com.dineth.debateTracker.debate.DebateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/debater")
public class DebaterController {
    private final DebaterService debaterService;
    private final BallotService ballotService;
    private final DebateService debateService;

    @Autowired
    public DebaterController(DebaterService debaterService, BallotService ballotService, DebateService debateService) {
        this.debaterService = debaterService;
        this.ballotService = ballotService;
        this.debateService = debateService;
    }

    @GetMapping
    public List<Debater> getDebaters() {
        return debaterService.getDebaters();
    }

    /**
     * Get debaters with the same name
     * @param birthdate - if true, also check for same birthdate
     * @return List of debaters with the same name
     */
    @GetMapping(path = "same")
    public List<Debater> getDebatersWithSameName(@RequestParam(required = false) String birthdate) {
        if (birthdate != null && birthdate.equalsIgnoreCase("true")) {
            return debaterService.findDebatersWithDuplicateNamesAndBirthdays();
        }
        return debaterService.findDebatersWithDuplicateNames();
    }

    /**
     * Replace one debater with another in all references in the database
     * @param values - oldDebaterId, newDebaterId
     */
    @PostMapping(path = "replace")
    public void replaceDebater(@RequestBody Map<String, String> values) {
        try {
            Long oldDebaterId = Long.parseLong(values.get("oldDebaterId"));
            Long newDebaterId = Long.parseLong(values.get("newDebaterId"));
            Debater oldDebater = debaterService.findDebaterById(oldDebaterId);
            Debater newDebater = debaterService.findDebaterById(newDebaterId);
            debaterService.replaceDebaters(oldDebater, newDebater);
        } catch (Exception e) {
            log.error("Error replacing debater: " + e.getMessage());
        }
    }

    @PostMapping
    public Debater addDebater(@RequestBody Debater debater) {
        return debaterService.addDebater(debater);
    }

    @GetMapping(path = "speaks/{debaterId}")
    public List<Float> getAllSpeaks(@PathVariable("debaterId") Long debaterId) {
        List<Ballot> ballots = ballotService.findBallotsByDebaterAndIsSubstantive(new Debater(debaterId));
        List<Float> speaks = new ArrayList<>();
        for (Ballot ballot : ballots) {
            speaks.add(ballot.getSpeakerScore());
        }
        return speaks;
    }

    @GetMapping(path = "stats")
    public Map<Debater, Object> getDebaterStats() {
        Map<Debater, Object> temp = debateService.getWinLossStats();
        Map<Debater, Object> debaterStats = new HashMap<>();
        for (Map.Entry<Debater, Object> entry : temp.entrySet()) {
            Debater debater = entry.getKey();
            Map<String, Integer> stats = (Map<String, Integer>) entry.getValue();
            int wins = stats.get("wins");
            int losses = stats.get("losses");
            int total = wins + losses;
            float winPercentage = (float) wins / total;
            stats.put("total", total);
            stats.put("winPercentage", (int) (winPercentage * 100));
            debaterStats.put(debater, stats);
        }
        return debaterStats;


    }


}

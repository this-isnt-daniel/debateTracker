package com.dineth.debateTracker.ballot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/")
    public List<Ballot> getAllBallots() {
        return ballotService.getBallots();
    }

}

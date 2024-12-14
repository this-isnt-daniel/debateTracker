package com.dineth.debateTracker.team;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/debater/{debaterId}")
    public List<Team> getTeamByDebaterId(@PathVariable Long debaterId) {
        return teamService.getTeamsByDebater(debaterId);
    }

}

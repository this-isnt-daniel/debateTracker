package com.dineth.debateTracker.team;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getTeam() {
        return teamRepository.findAll();
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }


}

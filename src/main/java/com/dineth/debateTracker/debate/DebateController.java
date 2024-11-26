package com.dineth.debateTracker.debate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController @Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/debate")
public class DebateController {

    private final DebateService debateService;

    @Autowired
    public DebateController(DebateService debateService) {
        this.debateService = debateService;
    }

    /**
     * Get all debates where the debater participated
     * @param debaterId Id of the debater
     * @return List of debates
     */
    @GetMapping("/debater")
    public List<Debate> getDebatesOfDebater(@RequestParam() Long debaterId) {
        return debateService.findDebatesByDebaterId(debaterId);
    }


}

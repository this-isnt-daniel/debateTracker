package com.dineth.debateTracker.debate;

import com.dineth.debateTracker.debater.Debater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
     * Get all prelims where the debater spoke
     * @param debaterId Id of the debater
     * @return List of debates
     */
    @GetMapping("/debater/prelims")
    public List<Debate> getDebatesOfDebater(@RequestParam() Long debaterId) {
        return debateService.findPrelimsByDebaterId(debaterId);
    }

    @GetMapping("/debater/breaks")
    public List<Debate> getBreaksOfDebater(@RequestParam() Long debaterId) {
        return debateService.findBreaksByDebaterId(debaterId);
    }
    @GetMapping("/head-to-head")
    public Map<Debater, Map<Debater,Integer>> getHeadToHead(@RequestParam(required = false) Long debater1Id, @RequestParam(required = false) Long debater2Id) {
        if (debater1Id == null || debater2Id == null) {
            return debateService.calculateHeadToHeadScore();
        }
        else {
         return null;
//         TODO implement this
        }
    }


}

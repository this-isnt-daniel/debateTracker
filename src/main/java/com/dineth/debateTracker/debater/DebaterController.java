package com.dineth.debateTracker.debater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/debater")
public class DebaterController {
    private DebaterService debaterService;
    @Autowired
    public DebaterController(DebaterService debaterService) {
        this.debaterService = debaterService;
    }

    @GetMapping
    public List<Debater> getDebaters() {
        return debaterService.getDebaters();
    }

    @GetMapping(path = "external")
    public void getDebatersFromAPI() {
        debaterService.getDebatersFromAPI();
    }

}

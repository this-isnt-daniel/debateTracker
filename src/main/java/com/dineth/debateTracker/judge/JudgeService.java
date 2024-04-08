package com.dineth.debateTracker.judge;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JudgeService {
    private final JudgeRepository judgeRepository;

    public JudgeService(JudgeRepository judgeRepository) {
        this.judgeRepository = judgeRepository;
    }

    public List<Judge> getJudge() {
        return judgeRepository.findAll();
    }

    public Judge addJudge(Judge judge) {
        return judgeRepository.save(judge);
    }


}

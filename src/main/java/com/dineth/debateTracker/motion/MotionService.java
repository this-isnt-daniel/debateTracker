package com.dineth.debateTracker.motion;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MotionService {
    private final MotionRepository motionRepository;

    public MotionService(MotionRepository motionRepository) {
        this.motionRepository = motionRepository;
    }

    public List<Motion> getMotion() {
        return motionRepository.findAll();
    }

    public Motion addMotion(Motion motion) {
        return motionRepository.save(motion);
    }


}

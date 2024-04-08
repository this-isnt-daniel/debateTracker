package com.dineth.debateTracker.motion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionRepository extends JpaRepository<Motion, Long> {
}

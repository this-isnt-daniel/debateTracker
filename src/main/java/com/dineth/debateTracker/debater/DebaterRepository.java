package com.dineth.debateTracker.debater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebaterRepository extends JpaRepository<Debater, Long> {
}

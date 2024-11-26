package com.dineth.debateTracker.debate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebateRepository extends JpaRepository<Debate, Long> {
    @Query("SELECT d FROM Debate d JOIN d.ballots b WHERE b.debater.id = :debaterId")
    List<Debate> findDebatesByDebaterId(@Param("debaterId") Long debaterId);
}

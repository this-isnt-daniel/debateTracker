package com.dineth.debateTracker.eliminationballot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EliminationBallotRepository extends JpaRepository<EliminationBallot, Long> {

}

package com.dineth.debateTracker.ballot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BallotRepository extends JpaRepository<Ballot, Long> {
}

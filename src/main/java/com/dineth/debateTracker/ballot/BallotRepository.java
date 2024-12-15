package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.debater.Debater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallotRepository extends JpaRepository<Ballot, Long> {
    List<Ballot> findBallotsByDebater(Debater debater);

    @Query(value = "SELECT j.fname, j.lname, array_agg(b.speaker_score) AS scores, AVG(b.speaker_score) AS avg_score, COUNT(b.speaker_score) AS rounds_judged " +
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "WHERE b.speaker_score > 40.5 " +
            "GROUP BY j.fname, j.lname " +
            "ORDER BY avg_score DESC",
            nativeQuery = true)
    List<Object[]> getRankedJudgesBySpeaks();
}

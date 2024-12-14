package com.dineth.debateTracker.judge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Judge findByFnameAndLname(String fname, String lname);

    /**
     * Get all tournaments and scores for a judge in the preliminary rounds
     * @param judgeId The ID of the judge
     */
    @Query(value = "SELECT t.id as tid, t.shortName, r.id as rid, r.roundName, b.speakerScore, b.speakerPosition " +
            "FROM Tournament t " +
            "JOIN t.rounds r " +
            "JOIN r.debates d " +
            "JOIN d.ballots b " +
            "JOIN b.judge j " +
            "WHERE b.judge.id = :judgeId " +
            "ORDER BY t.shortName, r.roundName")
    List<Object> findTournamentsAndScoresForJudge(@Param("judgeId") Long judgeId);

    /**
     * Get all tournaments and break rounds judged by a judge
     * @param judgeId The ID of the judge
     */
    @Query(value = "SELECT t.id as tid, t.shortName, r.id as rid, r.roundName " +
            "FROM Tournament t " +
            "JOIN t.rounds r " +
            "JOIN r.debates d " +
            "JOIN d.eliminationBallots eb " +
            "JOIN eb.judge j " +
            "WHERE eb.judge.id = :judgeId " +
            "ORDER BY t.shortName, r.roundName")
    List<Object> findTournamentsAndBreaksJudged(@Param("judgeId") Long judgeId);

}

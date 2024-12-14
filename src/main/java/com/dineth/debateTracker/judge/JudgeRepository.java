package com.dineth.debateTracker.judge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Judge findByFnameAndLname(String fname, String lname);

//    Get each judge and all the tournaments they've judged prelims at, along with the rounds they've judged
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT r.round_name ORDER BY r.round_name) AS round_numbers, " +
            "t.short_name AS tournament_name, " +
            "j.id " +
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "JOIN debate d ON b.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, t.short_name, j.id " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentWithPrelims();
    //    Get each judge and all the tournaments they've judged breaks at, along with the rounds they've judged
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT r.round_name ORDER BY r.round_name) AS round_numbers, " +
            "t.short_name AS tournament_name, " +
            "j.id " +
            "FROM elimination_ballot eb " +
            "JOIN judge j ON eb.judge_id = j.id " +
            "JOIN debate d ON eb.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, t.short_name, j.id " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentWithBreaks();

//    Get each judge and all the tournaments they've judged prelims at
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT t.short_name ORDER BY t.short_name) AS tournament_names, " +
            "j.id " +
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "JOIN debate d ON b.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, j.id " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentPrelims();

//    Get each judge and all the tournaments they've judged breaks at
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT t.short_name ORDER BY t.short_name) AS tournament_names, " +
            "j.id " +
            "FROM elimination_ballot eb " +
            "JOIN judge j ON eb.judge_id = j.id " +
            "JOIN debate d ON eb.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, j.id " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentBreaks();

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

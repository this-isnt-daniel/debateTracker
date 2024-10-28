package com.dineth.debateTracker.judge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Judge findByFnameAndLname(String fname, String lname);

//    Get each judge and all the tournaments they've judged prelims at, along with the rounds they've judged
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT r.round_name ORDER BY r.round_name) AS round_numbers, " +
            "t.short_name AS tournament_name " +
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "JOIN debate d ON b.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, t.short_name " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentWithPrelims();
    //    Get each judge and all the tournaments they've judged breaks at, along with the rounds they've judged
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT r.round_name ORDER BY r.round_name) AS round_numbers, " +
            "t.short_name AS tournament_name " +
            "FROM elimination_ballot eb " +
            "JOIN judge j ON eb.judge_id = j.id " +
            "JOIN debate d ON eb.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, t.short_name " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentWithBreaks();

//    Get each judge and all the tournaments they've judged prelims at
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT t.short_name ORDER BY t.short_name) AS tournament_names " +  // Added DISTINCT
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "JOIN debate d ON b.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentPrelims();

//    Get each judge and all the tournaments they've judged breaks at
    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT t.short_name ORDER BY t.short_name) AS tournament_names " +  // Added DISTINCT
            "FROM elimination_ballot eb " +
            "JOIN judge j ON eb.judge_id = j.id " +
            "JOIN debate d ON eb.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournamentBreaks();

}

package com.dineth.debateTracker.judge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Judge findByFnameAndLname(String fname, String lname);

    @Query(value = "SELECT j.fname, j.lname, " +
            "array_agg(DISTINCT r.round_name ORDER BY r.round_name) AS round_numbers, " +  // Added DISTINCT
            "t.short_name AS tournament_name " +
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "JOIN debate d ON b.debate_id = d.id " +
            "JOIN round r ON d.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "GROUP BY j.fname, j.lname, t.short_name " +
            "ORDER BY j.fname, j.lname",
            nativeQuery = true)
    List<Object[]> getJudgesByTournament();





}

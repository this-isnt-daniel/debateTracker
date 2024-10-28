package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.debater.Debater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallotRepository extends JpaRepository<Ballot, Long> {
    List<Ballot> findBallotsByDebater(Debater debater);

    List<Ballot> findBallotsByDebaterAndSpeakerScoreGreaterThan(Debater debater, Float speakerScore);

    @Query(value = "SELECT d.first_name, d.last_name, d.phone, array_agg(b.speaker_score) AS scores, t.short_name, r.round_no, tm.team_name " +
            "FROM ballot b " +
            "JOIN debater d ON b.debater_id = d.id " +
            "JOIN debate db ON b.debate_id = db.id " +
            "JOIN round r ON db.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "JOIN team_debaters td ON d.id = td.debaters_id " +
            "JOIN team tm ON td.team_id = tm.id " +
            "WHERE b.speaker_score > 40.5 " +
            "GROUP BY d.first_name, d.last_name, d.phone, t.short_name, r.round_no, tm.team_name " +
            "ORDER BY d.first_name, d.last_name",
            nativeQuery = true)
    List<Object[]> findDebaterScoresNative();

    @Query(value = "SELECT d.first_name, d.last_name, array_agg(b.speaker_score) AS scores, AVG(b.speaker_score) AS avg_score, COUNT(b.speaker_score) AS rounds_debated " +
            "FROM ballot b " +
            "JOIN debater d ON b.debater_id = d.id " +
            "WHERE b.speaker_score > 40.5 " +
            "GROUP BY d.first_name, d.last_name " +
            "ORDER BY avg_score DESC",
            nativeQuery = true)
    List<Object[]> getRankedDebaters();


    @Query(value = "SELECT d.first_name, d.last_name, array_agg(b.speaker_score) AS scores, AVG(b.speaker_score) AS avg_score, COUNT(b.speaker_score) AS rounds_debated " +
            "FROM ballot b " +
            "JOIN debater d ON b.debater_id = d.id " +
            "WHERE b.speaker_score > 40.5 AND d.first_name = ?1 AND d.last_name = ?2 " +
            "GROUP BY d.first_name, d.last_name " +
            "ORDER BY avg_score DESC",
            nativeQuery = true)
    List<Object[]> getRankedDebaters(String firstName, String lastName);


    @Query(value = "SELECT j.fname, j.lname, array_agg(b.speaker_score) AS scores, AVG(b.speaker_score) AS avg_score, COUNT(b.speaker_score) AS rounds_judged " +
            "FROM ballot b " +
            "JOIN judge j ON b.judge_id = j.id " +
            "WHERE b.speaker_score > 40.5 " +
            "GROUP BY j.fname, j.lname " +
            "ORDER BY avg_score DESC",
            nativeQuery = true)
    List<Object[]> getRankedJudgesBySpeaks();
}

package com.dineth.debateTracker.ballot;

import com.dineth.debateTracker.debater.Debater;
import com.dineth.debateTracker.judge.Judge;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity @Table(name="ballot") @Getter @Setter @NoArgsConstructor
public class Ballot implements Serializable {
    @Id
    private String id;
    @OneToOne
    private Debater prop1;
    private Float prop1Score;
    @OneToOne
    private Debater prop2;
    private Float prop2Score;
    @OneToOne
    private Debater prop3;
    private Float prop3Score;
    @OneToOne
    private Debater propReply;
    private Float propReplyScore;
    @OneToOne
    private Debater opp1;
    private Float opp1Score;
    @OneToOne
    private Debater opp2;
    private Float opp2Score;
    @OneToOne
    private Debater opp3;
    private Float opp3Score;
    @OneToOne
    private Debater oppReply;
    private Float oppReplyScore;
    @OneToOne
    private Judge judge;

    public String getWinner() {
        float propScore = prop1Score + prop2Score + prop3Score + propReplyScore;
        float oppScore = opp1Score + opp2Score + opp3Score + oppReplyScore;
        if (propScore > oppScore) {
            return "proposition";
        } else {
            return "opposition";
        }
    }

}

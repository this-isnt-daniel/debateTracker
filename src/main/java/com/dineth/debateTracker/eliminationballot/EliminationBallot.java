package com.dineth.debateTracker.eliminationballot;

import com.dineth.debateTracker.judge.Judge;
import com.dineth.debateTracker.team.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity @Table(name="elimination_ballot") @Getter @Setter @NoArgsConstructor
public class EliminationBallot implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elimination_ballot_seq")
    @SequenceGenerator(name = "elimination_ballot_seq", sequenceName = "elimination_ballot_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    private Judge judge;
    @ManyToOne
    private Team winner;
    @ManyToOne
    private Team loser;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public EliminationBallot(Judge judge, Team winner, Team loser) {
        this.judge = judge;
        this.winner = winner;
        this.loser = loser;
    }

    @Override
    public String toString() {
        return "EliminationBallot{" +
                "id=" + id +
                ", judge=" + judge +
                ", winner=" + winner +
                ", loser=" + loser +
                '}';
    }

}

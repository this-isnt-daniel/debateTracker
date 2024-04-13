package com.dineth.debateTracker.tournament;

import com.dineth.debateTracker.round.Round;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tournament")
@Getter
@Setter
@NoArgsConstructor
public class Tournament implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_seq")
    @SequenceGenerator(name = "tournament_seq", sequenceName = "tournament_seq", allocationSize = 1)
    private Long id;
    private String fullName;
    private String shortName;
    private Date date;

    @JoinColumn(name = "tournament_id")
    @OneToMany
    private List<Round> rounds;

    public Tournament(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

}

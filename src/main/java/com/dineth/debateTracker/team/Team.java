package com.dineth.debateTracker.team;

import com.dineth.debateTracker.debater.Debater;
import com.dineth.debateTracker.institution.Institution;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity @Table(name="team") @Getter @Setter @NoArgsConstructor
public class Team implements Serializable {
    @Id
    private String id;
    private String teamName;
    private String teamCode;
    @OneToOne
    private Institution institution;
    @OneToMany
    private List<Debater> debaters;

}

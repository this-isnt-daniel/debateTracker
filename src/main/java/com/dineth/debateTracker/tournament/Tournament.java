package com.dineth.debateTracker.tournament;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tournament") @Getter
@Setter
@NoArgsConstructor
public class Tournament implements Serializable {
    @Id
    private String id;
    private String fullName;
    private String shortName;
    private Date date;

}

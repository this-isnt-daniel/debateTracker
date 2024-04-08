package com.dineth.debateTracker.institution;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity @Table(name="institution") @Getter @Setter @NoArgsConstructor
public class Institution implements Serializable {
    @Id
    private String id;
    private String name;
    private String abbreviation;

}

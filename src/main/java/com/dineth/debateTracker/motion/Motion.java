package com.dineth.debateTracker.motion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity @Table(name="motion") @Getter @Setter @NoArgsConstructor
public class Motion implements Serializable {
    @Id
    private String id;
    private String code;
    private String motion;
    private String infoslide;

}

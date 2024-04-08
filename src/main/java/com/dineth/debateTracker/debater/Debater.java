package com.dineth.debateTracker.debater;

import com.dineth.debateTracker.institution.Institution;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity @Table(name="debater") @Getter @Setter @NoArgsConstructor
public class Debater implements Serializable {
    @Id
    private String id;
    private String fname;
    private String lname;
    private String gender;
    private String email;
    private String phone;
    private Date birthdate;
    @OneToOne
    private Institution currentInstitution;

    public Debater(String id, String fname, String lname, String gender, String email, String phone, Date birthdate) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Debater{" +
                "id='" + id + '\'' +
                ", name='" + fname + '\'' + " " + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}

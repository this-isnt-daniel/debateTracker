package com.dineth.debateTracker.judge;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity @Table(name="judge") @Getter @Setter @NoArgsConstructor
public class Judge implements Serializable {
    @Id
    private String id;
    private String fname;
    private String lname;
    private String gender;
    private String email;
    private String phone;
    private Date birthdate;
    private Float rating;

    public Judge(String id, String fname, String lname, String gender, String email, String phone, Date birthdate, Float rating) {
        this.id = id;
        this.rating = rating;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Judge{" +
                "id='" + id + '\'' +
                ", name='" + fname + '\'' + " " + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthdate=" + birthdate +
                ", rating=" + rating +
                '}';
    }
}

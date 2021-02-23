package com.victolee.signuplogin.domain.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    //상태메시지
    @Column
    private String bio;

    //성별
    @Column
    private String gender;

    @Column
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthdate;

    @Builder
    public MemberEntity(Long id, String email, String password, String bio, String gender, Date birthdate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        birthdate.toString();
        return birthdate;
    }
}
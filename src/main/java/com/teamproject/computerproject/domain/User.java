package com.teamproject.computerproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "member_id", length = 250)
    private String memberId;

    @Column(name = "member_pw")
    private String memberPw;

    @Column(name = "sns_token")
    private String snsToken;

}
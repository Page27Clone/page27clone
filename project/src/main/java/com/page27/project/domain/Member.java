package com.page27.project.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member extends Timestamped{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String loginId;

    private String password;

    @Embedded
    private Address address;

    private String phoneNumber;

    private String email;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    private int visitCount;

    private int orderCount;

    private int reserve;

    private LocalDateTime createdAt;
}

package com.page27.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
//@ToString(of = {"id","name","loginId","password","phoneNumber","email","birthday","memberGrade","visitCount","orderCount"})
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String loginId;

    private String password;

    private String phoneNumber;

    private String email;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    private int visitCount;

    private int orderCount;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "mileage_id")
    private Mileage mileage;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();


}

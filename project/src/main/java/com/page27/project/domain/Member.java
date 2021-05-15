package com.page27.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    //private String sex;

    private String loginId;

    private String password;

    private String phoneNumber;

    private String email;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    @Embedded
    private Address address;

    private int visitCount;

    private int orderCount;

    //private String HomePhoneNumber;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Mileage> mileageList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();


    //연관 관계 메소드

}

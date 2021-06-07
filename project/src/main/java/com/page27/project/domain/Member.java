package com.page27.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NonNull
    private String loginId;

    @NonNull
    private String password;

    @NonNull
    private String name;

    private String sex;

    private String email;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    @Embedded
    private Address address;

    private int visitCount;

    private int orderCount;

    private String phoneNumber;

    private String HomePhoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mileage> mileageList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    //연관 관계 메소드
    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

    @Builder
    public Member(Long id, String loginId, String password){
        this.id = id;
        this.loginId = loginId;
        this.password = password;
    }

}

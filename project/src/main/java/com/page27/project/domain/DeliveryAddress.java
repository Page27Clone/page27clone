package com.page27.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.jdo.annotations.Join;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
    private Long id;

    private String placeName;

    private String recipient;

    private String city;

    private String street;

    private String zipcode;

    private String homePhoneNumber;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //연관관계 메소드
    public void setMember(Member member){
        this.member = member;
        member.getDeliveryAddressList().add(this);
    }
}

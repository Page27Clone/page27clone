package com.page27.project.dto;

import com.page27.project.domain.MemberGrade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Long id;
    private String loginId;
    private String name;
    private MemberGrade memberGrade;
    private String city;
    private String street;
    private String zipcode;
    private String homePhoneNumber;
    private String phoneNumber;
    private String birthday;
    private String email;
}

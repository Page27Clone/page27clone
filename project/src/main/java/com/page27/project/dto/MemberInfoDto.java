package com.page27.project.dto;

import com.page27.project.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberInfoDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String homePhoneNumber;
    private String phoneNumber;
    private String email;
    private String birthday;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .loginId(loginId)
                .password(password)
                .name(name)
                .homePhoneNumber(homePhoneNumber)
                .phoneNumber(phoneNumber)
                .email(email)
                .birthday(birthday)

                .build();
    }

    @Builder
    public MemberInfoDto(Long id, String loginId, String password, String name, String homePhoneNumber, String phoneNumber, String email, String birthday) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.homePhoneNumber = homePhoneNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }
}// 회원가입을 위한 Dto
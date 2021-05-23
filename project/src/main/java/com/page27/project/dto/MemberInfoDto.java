package com.page27.project.dto;

import com.page27.project.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberInfoDto {
    //private Long id;
    private String loginId;
    private String password;

    public Member toEntity(){
        return Member.builder()
                //.id(id)
                .loginId(loginId)
                .password(password)
                .build();
    }

    @Builder
    public MemberInfoDto(Long id, String loginId, String password) {
        //this.id = id;
        this.loginId = loginId;
        this.password = password;
    }
}// 회원가입을 위한 Dto
package com.page27.project.dto;

import com.page27.project.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class MemberPageDto {
    Page<MemberDto> memberBoards;
    int homeStartPage;
    int homeEndPage;
}

package com.page27.project.repository;

import com.page27.project.domain.Member;
import com.page27.project.domain.MemberGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@Rollback(value = false)
public class AdminTest {

}

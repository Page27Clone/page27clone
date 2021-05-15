package com.page27.project;

import com.page27.project.querydsl.Hello;
import com.page27.project.querydsl.QHello;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class ProjectApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		Hello hello = new Hello();
		em.persist(hello);

		JPAQueryFactory query = new JPAQueryFactory(em);

		QHello qHello = QHello.hello; //Querydsl Q타입 동작 확인
		Hello result = query
				.selectFrom(qHello)
				.fetchOne();

		Assertions.assertThat(result).isEqualTo(hello);
		Assertions.assertThat(result.getId()).isEqualTo(hello.getId());
		//Assertions.assertThat(result).isEqualTo(hello);
		//lombok 동작 확인 (hello.getId())
		//Assertions.assertThat(result.getId()).isEqualTo(hello.getId());

	}
}

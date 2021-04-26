package com.page27.project;

import com.page27.project.domain.Address;
import com.page27.project.domain.Member;
import com.page27.project.domain.MemberGrade;
import com.page27.project.repository.MemberRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectApplication{

	public static void main(String[] args) {


		SpringApplication.run(ProjectApplication.class, args);
	}

}

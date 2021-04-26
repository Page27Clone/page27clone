package com.page27.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Reserve {
    @Id
    @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    private int reservePrice;
}

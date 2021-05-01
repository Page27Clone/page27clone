package com.page27.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Mileage {
    @Id
    @GeneratedValue
    @Column(name = "mileage_id")
    private Long id;

    private int mileagePrice;

    @OneToOne(mappedBy = "mileage",fetch = FetchType.LAZY)
    public Member member;

}

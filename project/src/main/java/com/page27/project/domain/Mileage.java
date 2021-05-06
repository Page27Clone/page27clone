package com.page27.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Mileage extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "mileage_id")
    private Long id;

    private int mileagePrice;

    private String mileageContent;

    @OneToOne(mappedBy = "mileage",fetch = FetchType.LAZY)
    private Member member;

    protected Mileage(){
        this.mileagePrice = 0;
    }
}

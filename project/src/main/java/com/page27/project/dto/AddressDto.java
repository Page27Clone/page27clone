package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String placeName;

    private String recipient;

    private String city;

    private String street;

    private String zipcode;

    private String addressHomePhoneNumber;

    private String addressPhoneNumber;
}

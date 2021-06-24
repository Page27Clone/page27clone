package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAddressDto {

    private String address_recipient;
    private String zipcode;
    private String city;
    private String street;
    private String addressHomePhoneNumber;
    private String addressPhoneNumber;
}

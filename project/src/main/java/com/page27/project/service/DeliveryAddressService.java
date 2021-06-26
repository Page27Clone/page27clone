package com.page27.project.service;

import com.page27.project.domain.DeliveryAddress;
import com.page27.project.dto.AddressChangeDto;
import com.page27.project.dto.AddressDto;

import java.util.List;

public interface DeliveryAddressService {

    void registerAddress(String loginId, AddressDto addressDto);
//    주소 등록 메소드

    DeliveryAddress findAddressById(Long id);
//    Pk를 이용해서 주소 찾는 메소드

    AddressChangeDto showAddressToChange(Long id);

    List<DeliveryAddress> getDeliveryAddressByLoginId(String loginId);
//    아이디를 이용해서 배송주소 찾는 메소드

    void deleteAddressById(Long id);
//    Pk를 이용해서 주소 삭제

    void updateDeliveryAddress(Long id, AddressChangeDto addressChangeDto);
//    배송 주소 업데이트하는 메소드
}

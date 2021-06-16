package com.page27.project.service;

import com.page27.project.domain.DeliveryAddress;
import com.page27.project.domain.Member;
import com.page27.project.dto.AddressDto;
import com.page27.project.repository.DeliveryAddressRepository;
import com.page27.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long registerAddress(String loginId, AddressDto addressDto){
        Optional<Member> byloginId = memberRepository.findByloginId(loginId);
        Member findMember = byloginId.get();

        DeliveryAddress deliveryAddress = new DeliveryAddress();

        deliveryAddress.setRecipient(addressDto.getRecipient());
        deliveryAddress.setCity(addressDto.getCity());
        deliveryAddress.setStreet(addressDto.getStreet());
        deliveryAddress.setZipcode(addressDto.getZipcode());
        deliveryAddress.setAddressPhoneNumber(addressDto.getAddressPhoneNumber());
        deliveryAddress.setAddressHomePhoneNumber(addressDto.getAddressHomePhoneNumber());
        deliveryAddress.setPlaceName(addressDto.getPlaceName());
        deliveryAddress.setMember(findMember);

        deliveryAddressRepository.save(deliveryAddress);

        return deliveryAddress.getId();
    }

    public List<DeliveryAddress> getDeliveryAddress(){
        return deliveryAddressRepository.findAll();
    }


}

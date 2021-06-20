package com.page27.project.service;

import com.page27.project.domain.DeliveryAddress;
import com.page27.project.domain.Member;
import com.page27.project.dto.AddressChangeDto;
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

    public AddressDto findAddressById(Long id){
        Optional<DeliveryAddress> byId = deliveryAddressRepository.findById(id);
        DeliveryAddress findDeliveryAddress = byId.get();

        AddressDto addressDto = new AddressDto();

        addressDto.setId(findDeliveryAddress.getId());
        addressDto.setPlaceName(findDeliveryAddress.getPlaceName());
        addressDto.setRecipient(findDeliveryAddress.getRecipient());
        addressDto.setZipcode(findDeliveryAddress.getZipcode());
        addressDto.setStreet(findDeliveryAddress.getStreet());
        addressDto.setCity(findDeliveryAddress.getCity());
        addressDto.setAddressHomePhoneNumber(findDeliveryAddress.getAddressHomePhoneNumber());
        addressDto.setAddressPhoneNumber(findDeliveryAddress.getAddressPhoneNumber());

        return addressDto;
    }

    public AddressDto getChangeAddressDto(){
        return null;
    }//수정중

    public List<DeliveryAddress> getDeliveryAddress(String loginId){
        return deliveryAddressRepository.findAllByMemberLoginId(loginId);
    }

    public Long deleteAddress(Long id){
        deliveryAddressRepository.deleteById(id);

        return id;
    }

    @Transactional
    public Long updateDeliveryAddress(Long id,AddressChangeDto addressChangeDto){
        Optional<DeliveryAddress> byId = deliveryAddressRepository.findById(id);
        DeliveryAddress deliveryAddress = byId.get();
        System.out.println("address phoneNumber0 : " + addressChangeDto.getAddressPhoneNumber()[0]);
        System.out.println("address phoneNumber 1: " + addressChangeDto.getAddressPhoneNumber()[1]);
        System.out.println("address phoneNumber 2: " + addressChangeDto.getAddressPhoneNumber()[2]);
        String addressPhoneNumberResult = addressChangeDto.getAddressPhoneNumber()[0] + "," + addressChangeDto.getAddressPhoneNumber()[1] + "," + addressChangeDto.getAddressPhoneNumber()[2];
        String addressHomePhoneNumberResult = addressChangeDto.getAddressHomePhoneNumber()[0] + "," + addressChangeDto.getAddressHomePhoneNumber()[1] + "," + addressChangeDto.getAddressHomePhoneNumber()[2];

        deliveryAddress.setPlaceName(addressChangeDto.getPlaceName());
        deliveryAddress.setRecipient(addressChangeDto.getRecipient());
        deliveryAddress.setAddressHomePhoneNumber(addressHomePhoneNumberResult);
        deliveryAddress.setAddressPhoneNumber(addressPhoneNumberResult);
        deliveryAddress.setZipcode(addressChangeDto.getZipcode());
        deliveryAddress.setCity(addressChangeDto.getCity());
        deliveryAddress.setStreet(addressChangeDto.getStreet());

        return deliveryAddress.getId();
    }

}

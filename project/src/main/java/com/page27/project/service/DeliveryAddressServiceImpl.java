package com.page27.project.service;

import com.page27.project.domain.DeliveryAddress;
import com.page27.project.domain.Member;
import com.page27.project.dto.AddressChangeDto;
import com.page27.project.dto.AddressDto;
import com.page27.project.exception.AddressNotFoundException;
import com.page27.project.exception.LoginIdNotFoundException;
import com.page27.project.repository.DeliveryAddressRepository;
import com.page27.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void registerAddress(String loginId, AddressDto addressDto) {
        Member findMember = memberRepository.findByloginId(loginId).orElseThrow(
                () -> new LoginIdNotFoundException("해당하는 회원이 존재하지 않습니다")
        );
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
    }

    @Override
    public DeliveryAddress findAddressById(Long id) {
        DeliveryAddress findDeliveryAddress = deliveryAddressRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException("해당하는 주소가 존재하지 않습니다")
        );

        return findDeliveryAddress;
    }

    @Override
    public AddressChangeDto showAddressToChange(Long id) {
        DeliveryAddress findDeliveryAddress = deliveryAddressRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException("해당하는 주소가 존재하지 않습니다")
        );
        AddressChangeDto addressChangeDto = new AddressChangeDto();

        String[] addressHomePhoneNumber = findDeliveryAddress.getAddressHomePhoneNumber().split(",");
        String[] addressPhoneNumber = findDeliveryAddress.getAddressPhoneNumber().split(",");

        addressChangeDto.setId(findDeliveryAddress.getId());
        addressChangeDto.setPlaceName(findDeliveryAddress.getPlaceName());
        addressChangeDto.setRecipient(findDeliveryAddress.getRecipient());
        addressChangeDto.setCity(findDeliveryAddress.getCity());
        addressChangeDto.setZipcode(findDeliveryAddress.getZipcode());
        addressChangeDto.setStreet(findDeliveryAddress.getStreet());
        addressChangeDto.setAddressPhoneNumber(addressPhoneNumber);
        addressChangeDto.setAddressHomePhoneNumber(addressHomePhoneNumber);

        return addressChangeDto;
    }


    @Override
    public List<DeliveryAddress> getDeliveryAddressByLoginId(String loginId) {

        return deliveryAddressRepository.findAllByMemberLoginId(loginId);
    }
//    회원에게 등록된 주소 모두 표시

    @Override
    public void deleteAddressById(Long id) {
        deliveryAddressRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateDeliveryAddress(Long id, AddressChangeDto addressChangeDto) {
        Optional<DeliveryAddress> findDeliveryAddress = deliveryAddressRepository.findById(id);
        DeliveryAddress deliveryAddress = findDeliveryAddress.orElseThrow(
                () -> new AddressNotFoundException("해당하는 주소가 존재하지 않습니다")
        );

        String addressPhoneNumberResult = addressChangeDto.getAddressPhoneNumber()[0] + "," + addressChangeDto.getAddressPhoneNumber()[1] + "," + addressChangeDto.getAddressPhoneNumber()[2];
        String addressHomePhoneNumberResult = addressChangeDto.getAddressHomePhoneNumber()[0] + "," + addressChangeDto.getAddressHomePhoneNumber()[1] + "," + addressChangeDto.getAddressHomePhoneNumber()[2];

        deliveryAddress.setPlaceName(addressChangeDto.getPlaceName());
        deliveryAddress.setRecipient(addressChangeDto.getRecipient());
        deliveryAddress.setAddressHomePhoneNumber(addressHomePhoneNumberResult);
        deliveryAddress.setAddressPhoneNumber(addressPhoneNumberResult);
        deliveryAddress.setZipcode(addressChangeDto.getZipcode());
        deliveryAddress.setCity(addressChangeDto.getCity());
        deliveryAddress.setStreet(addressChangeDto.getStreet());
    }

}

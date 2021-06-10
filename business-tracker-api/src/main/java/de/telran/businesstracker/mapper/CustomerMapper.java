package de.telran.businesstracker.mapper;

import de.telran.businesstracker.data.CustomerData;
import de.telran.businesstracker.dto.CustomerDataDto;

public class CustomerMapper {
    public CustomerDataDto toDto(CustomerData customerData) {
        return CustomerDataDto.builder()
                .email(customerData.getEmail())
                .formId(customerData.getFormId())
                .firstName(customerData.getFirstName())
                .lastName(customerData.getLastName())
                .interest(customerData.getInterest())
                .businessGoals(customerData.getBusinessGoals())
                .engLvl(customerData.getEngLvl())
                .linkedInUrl(customerData.getLinkedInUrl())
                .phoneNumber(customerData.getPhoneNumber())
                .startupPos(customerData.getStartupPos())
                .videoUrl1(customerData.getVideoUrl1())
                .videoUrl2(customerData.getVideoUrl2())
                .build();
    }
}

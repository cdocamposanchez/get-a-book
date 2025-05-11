package com.adi.gab.application.mapper;

import com.adi.gab.application.dto.AddressDTO;
import com.adi.gab.domain.valueobject.Address;
import com.adi.gab.infrastructure.persistance.embeddable.AddressEmbeddable;

public class AddressMapper {

    public static Address toDomain(AddressDTO dto) {
        return Address.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .emailAddress(dto.getEmailAddress())
                .addressLine(dto.getAddressLine())
                .city(dto.getCity())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .build();
    }

    public static AddressDTO toDTO(Address address) {
        return AddressDTO
                .builder()
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .emailAddress(address.getEmailAddress())
                .addressLine(address.getAddressLine())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address toDomain(AddressEmbeddable embeddable) {
        return Address.builder()
                .firstName(embeddable.getFirstName())
                .lastName(embeddable.getLastName())
                .emailAddress(embeddable.getEmailAddress())
                .addressLine(embeddable.getAddressLine())
                .city(embeddable.getCity())
                .country(embeddable.getCountry())
                .zipCode(embeddable.getZipCode())
                .build();
    }

    private AddressMapper() {}
}

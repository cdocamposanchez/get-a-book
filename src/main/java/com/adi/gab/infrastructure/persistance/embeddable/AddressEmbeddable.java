package com.adi.gab.infrastructure.persistance.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressEmbeddable {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String addressLine;
    private String city;
    private String zipCode;
    private String country;

    public AddressEmbeddable() {}
}
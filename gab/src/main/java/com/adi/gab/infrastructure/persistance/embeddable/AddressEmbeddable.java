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
    private String street;
    private String city;
    private String postalCode;
    private String country;

    protected AddressEmbeddable() {}
}
package com.adi.gab.domain.valueobject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Address {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String addressLine;
    private String country;
    private String city;
    private String zipCode;

    private Address(String firstName, String lastName, String emailAddress, String addressLine, String country, String city, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.addressLine = addressLine;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
    }

    public static Address of(String firstName, String lastName, String emailAddress, String addressLine, String country, String city, String zipCode) {

        return new Address(firstName, lastName, emailAddress, addressLine, country, city, zipCode);
    }
}

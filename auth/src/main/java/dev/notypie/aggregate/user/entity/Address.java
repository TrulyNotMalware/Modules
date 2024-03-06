package dev.notypie.aggregate.user.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Address {
    // Nullable.
    private String country;

    private String streetAddress;

    private String city;

    private String region;

    private String zipCode;

    @Builder
    Address(String country, String streetAddress, String city, String region, String zipCode){
        this.country = country;
        this.streetAddress = streetAddress;
        this.city = city;
        this.region = region;
        this.zipCode = zipCode;
    }

    @Builder(builderMethodName = "emptyAddress")
    Address(){
        this.country = null;
        this.streetAddress = null;
        this.city = null;
        this.region = null;
        this.zipCode = null;
    }
}

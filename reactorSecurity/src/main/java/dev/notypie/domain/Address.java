package dev.notypie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


class Address {
    @JsonProperty("country")
    private String country;

    @JsonProperty("street_address")
    private String streetAddress;

    @JsonProperty("city")
    private String city;

    @JsonProperty("region")
    private String region;

    @JsonProperty("zip_code")
    private String zipCode;


}

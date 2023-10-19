package dev.notypie.builders;

import dev.notypie.dto.UserRegisterDto;

public final class UserRegisterDtoBuilder {
    public String userId = "testUserId";
    public String userName = "testUserName";
    public String email = "thisistest@test.email";
    public String password = "testPassword";
    public String phoneNumber = "010-1234-5678";
    public String country = "testCountry";
    public String streetAddress = "testStreetAddress";
    public String city = "testCity";
    public String region = "testRegion";
    public String zipCode = "testZipCode";
    public UserRegisterDto build(){
        return UserRegisterDto.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();
    }


}
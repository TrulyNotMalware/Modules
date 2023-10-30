package dev.notypie.builders;

import dev.notypie.domain.Users;
import dev.notypie.jwt.dto.LoginRequestDto;
import dev.notypie.jwt.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class MockUserBuilders {

    public static final Long id = 1L;
    public static final String userId = "testUserId";
    public static final String userName = "testUserName";
    public static final String email = "thisistest@test.email";
    public static final String password = "testPassword";
    public static final String phoneNumber = "010-1234-5678";
    public static final String country = "testCountry";
    public static final String streetAddress = "testStreetAddress";
    public static final String city = "testCity";
    public static final String region = "testRegion";
    public static final String zipCode = "testZipCode";


    public static Users createDefaultUsers(){
        return Users.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .phoneNumber(phoneNumber)
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();
    }

    public static Users createDefaultUsers(String userId){
        return Users.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .phoneNumber(phoneNumber)
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();
    }

    public static Users createDefaultUsers(String userId, String userName, String password){
        return Users.builder()
                .userId(userId)
                .userName(userName)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .phoneNumber(phoneNumber)
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();
    }

    public static UserDto createDefaultUserDto(){
        return UserDto.builder()
                .id(id)
                .userId(userId)
                .email(email)
                .dtype("user")
                .build();
    }

    public static LoginRequestDto exchange(Users target){
        return LoginRequestDto.builder()
                .userId(target.getUserId())
                .password(target.getPassword())
                .build();
    }
}

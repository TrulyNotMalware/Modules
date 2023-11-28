package dev.notypie.exchanger;

import dev.notypie.domain.Users;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.jwt.dto.LoginRequestDto;
import dev.notypie.jwt.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class UserInfoExchanger {

    @Deprecated
    public static UserDto exchangeToUserDto(Users user){
        return UserDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .email(user.getEmail())
                .dtype("user")
                .build();
    }

    public static Users exchangeToUsers(UserRegisterDto dto){
        return Users.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .password(new BCryptPasswordEncoder().encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .city(dto.getCity())
                .email(dto.getEmail())
                .country(dto.getCountry())
                .streetAddress(dto.getStreetAddress())
                .zipCode(dto.getZipCode())
                .build();
    }

    public static LoginRequestDto exchangeToLoginRequestDto(UserRegisterDto dto){
        return LoginRequestDto.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .build();
    }
}

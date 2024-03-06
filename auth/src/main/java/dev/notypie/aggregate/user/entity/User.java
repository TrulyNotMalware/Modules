package dev.notypie.aggregate.user.entity;

import dev.notypie.constants.Constants;
import dev.notypie.domain.AggregateRoot;
import dev.notypie.dto.UserRegisterDto;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import static dev.notypie.global.util.Util.validateString;

@Getter
public class User extends AggregateRoot {

    @NotNull
    private final Long id;

    @NotBlank
    private final String userId;
    private final String userIdRegex = "[^a-zA-Z0-9]";

    @Email
    private final String email;

    @NotBlank
    private final String role;

    private final MutableUserInformation userInformation;

    private User(@NotBlank String userId, @NotBlank String userName, @NotBlank String password,
                @Email String email, @NotBlank String phoneNumber){
        this.id = 0L;
        this.userId = userId;
        this.email = email;
        this.userInformation = MutableUserInformation.builder()
                .userName(userName)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
        this.role = Constants.USER_ROLE_DEFAULT;
    }

    private User(
            @NotNull Long id, @NotBlank String userId, @Email String email,
            @NotNull MutableUserInformation userInformation
                 ){
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.userInformation = userInformation;
        this.role = Constants.USER_ROLE_DEFAULT;
    }

    private User(UserRegisterDto userRegisterDto){
        this.id = 0L;
        this.userId = userRegisterDto.getUserId();
        this.email = userRegisterDto.getEmail();
        this.userInformation = MutableUserInformation.builder()
                .userName(userRegisterDto.getUserName())
                .password(userRegisterDto.getPassword())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .build();
        this.role = Constants.USER_ROLE_DEFAULT;
    }


    @Builder(builderMethodName = "fromRegisterDto")
    public static User createUser(UserRegisterDto registerDto){
        return createInstance(() -> new User(registerDto));
    }

    @Builder(builderMethodName = "newUser")
    public static User createUser(@NotBlank String userId, @NotBlank String userName, @NotBlank String password,
                                  @Email String email, @NotBlank String phoneNumber){
        return createInstance(() -> new User(userId, userName, password, email, phoneNumber));
    }

    @Builder(builderMethodName = "toEntity")
    protected static User createUser(@NotNull Long id, @NotBlank String userId, @NotBlank String userName, @NotBlank String password,
                                     @Email String email, @NotBlank String phoneNumber,
                                     String country, String streetAddress, String city, String region, String zipCode){
        return createInstance(()-> new User(
                id, userId, email, MutableUserInformation.all()
                .userName(userName).password(password).phoneNumber(phoneNumber)
                .country(country).streetAddress(streetAddress).city(city).region(region).zipCode(zipCode)
                .build()
        ));
    }

    @Override
    protected void validate() {
        validateString(this.userIdRegex, this.userId);
        this.userInformation.validationUserInformation();
    }

}

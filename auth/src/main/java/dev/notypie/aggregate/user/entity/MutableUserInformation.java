package dev.notypie.aggregate.user.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static dev.notypie.global.util.Util.validateString;

@Getter
public class MutableUserInformation {

    @NotBlank
    private String userName;
    private final String userNameRegex = "[^a-zA-Z0-9_]";

    @NotBlank
    private String password;
    private final String passwordRegex = "[^a-zA-Z0-9!#@]";

    @NotBlank
    private String phoneNumber;
    private final String phoneNumberRegex = "^01(?:0|1|[6-9])[ .-]?(\\d{3}|\\d{4})[ .-]?(\\d{4})$";

    private final Address address;

    @Builder
    MutableUserInformation(
            @NotBlank String userName, @NotBlank String password, @NotBlank String phoneNumber){
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = Address.emptyAddress().build();
    }

    @Builder(builderMethodName = "all")
    MutableUserInformation(
            @NotBlank String userName, @NotBlank String password, @NotBlank String phoneNumber,
            String country, String streetAddress, String city, String region, String zipCode){
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = Address.builder()
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();
    }

    void validationUserInformation(){
        validateString(this.userNameRegex, userName);
        validateString(this.passwordRegex, password);
        validateString(this.phoneNumberRegex, phoneNumber);
    }

    void changePassword(String newPassword){
        validateString(this.passwordRegex, newPassword);
        this.password = newPassword;
    }

    void changeUserName(String userName){
        validateString(this.userNameRegex, userName);
        this.userName = userName;
    }

    void changePhoneNumber(String phoneNumber){
        validateString(this.phoneNumberRegex, phoneNumber);
        this.phoneNumber = phoneNumber;
    }
}

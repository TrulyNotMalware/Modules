package dev.notypie.infrastructure.dao.user.jpa;


import dev.notypie.aggregate.user.entity.MutableUserInformation;
import dev.notypie.aggregate.user.entity.User;
import dev.notypie.domain.Address;
import dev.notypie.domain.Converter;
import dev.notypie.domain.Users;

public class JpaDomainConverter implements Converter<User, Users> {

    @Override
    public Users convert(User user) {
        MutableUserInformation userInformation = user.getUserInformation();
        return Users.builder()
                .userId(user.getUserId())
                .userName(userInformation.getUserName())
                .phoneNumber(userInformation.getPhoneNumber())
                .password(userInformation.getPassword())
                .email(user.getEmail())
                .region(userInformation.getAddress().getRegion())
                .zipCode(userInformation.getAddress().getZipCode())
                .streetAddress(userInformation.getAddress().getStreetAddress())
                .country(userInformation.getAddress().getCountry())
                .city(userInformation.getAddress().getCity())
                .build();
    }

    @Override
    public User convert(Users users) {
        Address address = users.getAddress();
        return User.toEntity()
                .id(users.getId())
                .userId(users.getUserId())
                .userName(users.getUserName())
                .email(users.getEmail())
                .region(address.getRegion())
                .streetAddress(address.getStreetAddress())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .password(users.getPassword())
                .phoneNumber(users.getPhoneNumber())
                .build();
    }
}

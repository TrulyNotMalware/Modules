package dev.notypie.domain;

import dev.notypie.jwt.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor Do not use AllArgsConstructor
@DiscriminatorColumn(name = "DTYPE")
@SequenceGenerator(
        name = "USER_SQ_GENERATOR",
        sequenceName = "USER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ"
    )
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "User id must required.")
    @Pattern(regexp = "[a-zA-Z0-9_-]*$")
    @Column(name = "user_id")
    private String userId;

    @NotBlank(message = "User name must required.")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\\\d{2,3}-\\\\d{3,4}-\\\\d{4}$", message = "incorrect phone number format.")
    private String phoneNumber;

    @Embedded
    private Address address;

    @Embedded
    private RefreshToken refreshToken;

    @Column(insertable = false, updatable = false)
    protected String dtype;

    @Builder
    protected Users(String userId, String userName, String password, String email, String phoneNumber,
                    String country, String streetAddress, String city, String region, String zipCode){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = Address.builder()
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();
    }

    public UserDetails createUserSecurity(){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.dtype));
        return new User(this.id.toString(), this.password, authorities);
    }

    public UserDto toUserDto(){
        return UserDto.builder()
                .id(this.id)
                .userId(this.userId)
                .email(this.email)
                .dtype(this.dtype).build();
    }

    public Users updateRefreshToken(String newRefreshToken){
        this.refreshToken.update(newRefreshToken);
        return this;
    }

    public String getRefreshToken(){
        return this.refreshToken.getRefreshToken();
    }
}

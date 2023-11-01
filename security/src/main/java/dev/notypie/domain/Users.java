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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

import static dev.notypie.constants.Constants.USER_ROLE_DEFAULT;

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
    @Column(name = "user_id", unique = true)
    private String userId;

    @NotBlank(message = "User name must required.")
    @Pattern(regexp = "^[a-zA-Z0-9 _-]*$")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    @Email
    private String email;

    //10.19 Add role
    @Column(name = "role")
    private String role;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "phone_number")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "incorrect phone number format.")
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
        if(password == null) this.password = "OAuthUser";
        else this.password = new BCryptPasswordEncoder().encode(password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = Address.builder()
                .country(country)
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .zipCode(zipCode)
                .build();

        this.role = USER_ROLE_DEFAULT;
    }

    public Users updateUsers(Users updateInfo){
        if(!this.userName.equals(updateInfo.getUserName()) && updateInfo.getUserName() != null) this.userName = updateInfo.getUserName();
        // Now email is cannot update.
//        if(!this.email.equals(updateInfo.getEmail()) && updateInfo.getEmail() != null) this.email = updateInfo.getEmail();
        if(this.address!= null)
            if(!this.address.equals(updateInfo.getAddress())) this.address = updateInfo.getAddress();
        return this;
    }

    public UserDetails createUserSecurity(){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role));
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
        if( this.refreshToken == null)
            this.refreshToken = RefreshToken.builder().build();
        this.refreshToken.update(newRefreshToken);
        return this;
    }

    public String getRefreshToken(){
        //9.12 if refreshToken not exists,
        if(this.refreshToken != null)
            return this.refreshToken.getRefreshToken();
        else return null;
    }
}

package dev.notypie.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}

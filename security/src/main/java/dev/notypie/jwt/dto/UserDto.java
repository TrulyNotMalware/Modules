package dev.notypie.jwt.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userId;
    private String email;
    private String dtype;
}
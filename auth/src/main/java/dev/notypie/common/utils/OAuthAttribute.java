package dev.notypie.common.utils;

import dev.notypie.domain.Users;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public enum OAuthAttribute {
    GITHUB("github", (attributes) ->
            Users.builder()
            .userId(String.valueOf(attributes.get("id")))
            .userName(String.valueOf(attributes.get("name")))
            .email(String.valueOf(attributes.get("email")))
            .build()),
    MYSERVICE("myservice", (attributes) ->
            Users.builder()
                    .userId(String.valueOf(attributes.get("userId")))
                    .build())
    ;

    private final String registrationId;
    private final Function<Map<String, Object>, Users> of;

    public static Users extract(String registrationId, Map<String, Object> attributes){
        return Arrays.stream(values())
                .filter(oAuthAttribute -> registrationId.equals(oAuthAttribute.registrationId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .of.apply(attributes);
    }
}

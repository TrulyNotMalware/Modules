package dev.notypie.common.utils;

import dev.notypie.domain.Users;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttribute {
    GITHUB("github", (attributes) ->
            Users.builder()
            .userId(String.valueOf(attributes.get("id")))
            .userName(String.valueOf(attributes.get("name")))
            .email(String.valueOf(attributes.get("email")))
            .build());

    private final String registrationId;
    private final Function<Map<String, Object>, Users> of;

    OAuthAttribute(String registrationId, Function<Map<String, Object>, Users> of){
        this.registrationId = registrationId;
        this.of = of;
    }

    public static Users extract(String registrationId, Map<String, Object> attributes){
        return Arrays.stream(values())
                .filter(oAuthAttribute -> registrationId.equals(oAuthAttribute.registrationId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .of.apply(attributes);
    }
}

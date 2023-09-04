package dev.notypie.jwt;

import org.springframework.security.core.context.SecurityContextHolder;

public class UsersJwtContextHolder {
    public static UsersJwtToken getUsersJwtToken(){
        return (UsersJwtToken) SecurityContextHolder.getContext().getAuthentication();
    }
}

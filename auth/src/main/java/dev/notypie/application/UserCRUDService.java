package dev.notypie.application;

import dev.notypie.dto.UserRegisterDto;
import dev.notypie.jwt.dto.UserDto;

public interface UserCRUDService {

    UserDto register(UserRegisterDto userRegisterDto);

}

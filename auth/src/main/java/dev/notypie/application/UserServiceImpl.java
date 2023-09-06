package dev.notypie.application;

import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.jwt.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserCRUDService, UserDetailsService {
    private final UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return this.repository.findByUserIdWithException(userId).createUserSecurity();
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        Users user = Users.builder()
                .userId(userRegisterDto.getUserId())
                .userName(userRegisterDto.getUserName())
                .password(userRegisterDto.getPassword())
                .email(userRegisterDto.getEmail())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .city(userRegisterDto.getCity())
                .country(userRegisterDto.getCountry())
                .region(userRegisterDto.getRegion())
                .streetAddress(userRegisterDto.getStreetAddress())
                .zipCode(userRegisterDto.getZipCode())
                .build();
        return this.repository.save(user).toUserDto();
    }

    
}

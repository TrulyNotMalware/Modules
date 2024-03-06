package dev.notypie.application;

import dev.notypie.aggregate.user.entity.User;
import dev.notypie.aggregate.user.repository.UserDomainRepository;
import dev.notypie.dto.UserRegisterDto;
import dev.notypie.jwt.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserCRUDService, UserDetailsService {
    private final UserDomainRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = this.repository.findByUserId(userId);
        return this.createUserSecurity(user);
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        User newUser = User.createUser(userRegisterDto);
        return this.toUserDto(this.repository.save(newUser));
    }

    private UserDetails createUserSecurity(User user){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getUserInformation().getPassword(), authorities);
    }

    private UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId()).userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }
}

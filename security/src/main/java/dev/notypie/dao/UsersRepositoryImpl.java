package dev.notypie.dao;

import dev.notypie.domain.Users;
import dev.notypie.exceptions.UserDomainException;
import dev.notypie.exceptions.UserErrorCodeImpl;
import dev.notypie.global.error.ArgumentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository{

    private final UserRepository repository;

    @Override
    public Optional<Users> findById(Long id) {
        return this.repository.findById(id);
    }

    //FIXME UserNotFoundException
    @Override
    public Users findByIdWithException(Long id) {
        return this.repository.findById(id).orElseThrow(() -> {
            List<ArgumentError> errors = new ArrayList<>();
            ArgumentError argumentError = new ArgumentError("id",id.toString(),"id not found in repository.");
            errors.add(argumentError);
            return UserDomainException.builder()
                    .errorCode(UserErrorCodeImpl.USER_NOT_FOUND)
                    .argumentErrors(errors).build();
        });
    }

    @Override
    public Users findByUserIdWithException(String userId) {
        return this.repository.findByUserId(userId).orElseThrow(() -> {
            List<ArgumentError> errors = new ArrayList<>();
            ArgumentError argumentError = new ArgumentError("User Id",userId,"User id not found in repository.");
            errors.add(argumentError);
            return UserDomainException.builder()
                    .errorCode(UserErrorCodeImpl.USER_NOT_FOUND)
                    .argumentErrors(errors).build();
        });
    }

    @Override
    public Users save(Users users) {
        return this.repository.save(users);
    }

    //FIXME UserNotFoundException.
    @Override
    @Transactional
    public Users updateRefreshToken(Long id, String refreshToken){
        Users users = this.findByIdWithException(id).updateRefreshToken(refreshToken);
        log.info("users : {}",users);
        return this.repository.save(users);
//                .map(user -> user.updateRefreshToken(refreshToken))
//                .map(this.repository::save)
//                .orElseThrow(() -> new RuntimeException("userNotFound."));
    }

    @Override
    public String findRefreshTokenById(Long id) {
        return this.findByIdWithException(id).getRefreshToken();
    }

    @Override
    public Users saveOrUpdateByUserId(Users users) {
        log.info("Users : {}",users);
        Users user = this.repository.findByUserId(users.getUserId())
                .map(findUser -> findUser.updateUsers(users))
                .orElse(users);
        return this.repository.save(user);
    }

}

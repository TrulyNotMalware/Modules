package dev.notypie.dao;

import dev.notypie.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("userNotFound"));
    }

    @Override
    public Users findByUserIdWithException(String userId) {
        return this.repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("userNotFound"));
    }

    @Override
    public Users save(Users users) {
        return this.repository.save(users);
    }

    //FIXME UserNotFoundException.
    @Override
    public Users updateRefreshToken(Long id, String refreshToken){
        return this.repository.findById(id)
                .map(user -> user.updateRefreshToken(refreshToken))
                .map(this.repository::save)
                .orElseThrow(() -> new RuntimeException("userNotFound."));
    }

    @Override
    public String findRefreshTokenById(Long id) {
        return this.repository.findById(id)
                .map(Users::getRefreshToken)
                .orElseThrow(() -> new RuntimeException("userNotFound."));
    }

}

package dev.notypie.dao;

import dev.notypie.domain.Users;
import java.util.Optional;

public interface UsersRepository {

    Optional<Users> findById(Long id);
    Users findByIdWithException(Long id);
    Users findByUserIdWithException(String userId);
    Users save(Users users);
    Users updateRefreshToken(Long id, String refreshToken);
    String findRefreshTokenById(Long id);
    Users saveOrUpdateByUserId(Users users);

}

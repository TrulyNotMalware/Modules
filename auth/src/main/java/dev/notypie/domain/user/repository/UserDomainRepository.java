package dev.notypie.domain.user.repository;

import dev.notypie.domain.user.entity.User;

public interface UserDomainRepository {

    User findById(Long id);
    User findByUserId(String userId);

    User save(User user);
}

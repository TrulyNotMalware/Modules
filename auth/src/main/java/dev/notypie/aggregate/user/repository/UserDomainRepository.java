package dev.notypie.aggregate.user.repository;

import dev.notypie.aggregate.user.entity.User;

public interface UserDomainRepository {

    User findById(Long id);
    User findByUserId(String userId);

    User save(User user);
}

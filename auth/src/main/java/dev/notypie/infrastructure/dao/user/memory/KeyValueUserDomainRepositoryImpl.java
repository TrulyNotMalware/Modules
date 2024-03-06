package dev.notypie.infrastructure.dao.user.memory;

import dev.notypie.aggregate.user.entity.User;
import dev.notypie.aggregate.user.repository.UserDomainRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KeyValueUserDomainRepositoryImpl implements UserDomainRepository {

    private final Map<Long, User> database = new ConcurrentHashMap<>();


    @Override
    public User findById(Long id) {
        return this.database.get(id);
    }

    @Override
    public User findByUserId(String userId) {
        return this.database.values().stream()
                .filter(targetUser -> targetUser.getUserId().equals(userId))
                .findFirst().orElseThrow();
    }

    @Override
    public User save(User user) {
        return this.database.put(user.getId(), user);
    }
}

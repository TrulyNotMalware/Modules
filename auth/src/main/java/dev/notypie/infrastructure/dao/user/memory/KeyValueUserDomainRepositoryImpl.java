package dev.notypie.infrastructure.dao.user.memory;

import dev.notypie.domain.user.entity.User;
import dev.notypie.domain.user.repository.UserDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KeyValueUserDomainRepositoryImpl implements UserDomainRepository {
    private final PasswordEncoder passwordEncoder;

    //FIXME when successfully authenticated, refreshTokenService.isDuplicateRefreshToken(id) throw exception cause there's no database.
    private final Map<Long, User> database = new ConcurrentHashMap<>();

    KeyValueUserDomainRepositoryImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

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
        encryptPassword(user);
        this.database.put(user.getId(), user);
        return this.database.get(user.getId());
    }

    private void encryptPassword(User user){
        String password = user.getUserInformation().getPassword();
        user.changePassword(this.passwordEncoder.encode(password));
    }
}

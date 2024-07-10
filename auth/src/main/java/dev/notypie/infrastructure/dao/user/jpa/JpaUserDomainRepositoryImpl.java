package dev.notypie.infrastructure.dao.user.jpa;

import dev.notypie.domain.user.entity.User;
import dev.notypie.domain.user.repository.UserDomainRepository;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Converter;
import dev.notypie.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JpaUserDomainRepositoryImpl implements UserDomainRepository {

    private final UsersRepository usersRepository;
    private final Converter<User, Users> converter;

    @Override
    public User findById(Long id) {
        Users jpaEntity = this.usersRepository.findByIdWithException(id);
        return this.converter.convert(jpaEntity);
    }

    @Override
    public User findByUserId(String userId) {
        Users jpaEntity = this.usersRepository.findByUserIdWithException(userId);
        return this.converter.convert(jpaEntity);
    }

    @Override
    public User save(User user) {
        Users jpaEntity = this.converter.convert(user);
        Users entity = this.usersRepository.save(jpaEntity);
        return this.converter.convert(entity);
    }
}

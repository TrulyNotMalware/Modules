package dev.notypie.dao;

import dev.notypie.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserId(String userId);
}
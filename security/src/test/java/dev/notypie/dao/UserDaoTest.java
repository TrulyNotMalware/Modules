package dev.notypie.dao;

import dev.notypie.base.annotations.H2JpaRepositoryTest;
import dev.notypie.builders.MockUserBuilders;
import dev.notypie.domain.Users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Tag("security")
@H2JpaRepositoryTest
@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserRepository userRepository;

    private UsersRepository domainRepository;
    Users user;
    Long id;

    @BeforeEach
    void setUp(){
        //insert one user.
        this.user = this.userRepository.save(MockUserBuilders.createDefaultUsers());
        this.id = this.userRepository.findByUserId(this.user.getUserId()).orElseThrow().getId();
        this.domainRepository = new UsersRepositoryImpl(this.userRepository);
    }

    @Test
    @DisplayName("[mod.Security] Dao select successfully works.")
    void findById() {
        //given
        //when
        Users findUser = this.domainRepository.findByIdWithException(this.id);
        //then
        Assertions.assertNotNull(findUser);
        Assertions.assertEquals(findUser.getUserId(), this.user.getUserId());
        Assertions.assertEquals(findUser.getUserName(), this.user.getUserName());
        Assertions.assertEquals(findUser.getPassword(), this.user.getPassword());
    }


    @Test
    @DisplayName("[mod.Security] Dao select successfully works")
    void findByUserIdWithException() {
        //given
        //when
        Users findUser = this.domainRepository.findByUserIdWithException(this.user.getUserId());
        //then
        Assertions.assertNotNull(findUser);
        Assertions.assertEquals(findUser.getUserId(), this.user.getUserId());
        Assertions.assertEquals(findUser.getUserName(), this.user.getUserName());
        Assertions.assertEquals(findUser.getPassword(), this.user.getPassword());
    }

    @Test
    @DisplayName("[mod.Security] Save failed when duplicate unique key 'userId'")
    void saveFailedWhenDuplicateUserIdDetected() {
        //given
        Users newUser = MockUserBuilders.createDefaultUsers();
        //when & then
        try {
            this.domainRepository.save(newUser);
            Users inserted = this.domainRepository.findByUserIdWithException(newUser.getUserId());
            Assertions.fail();
        }catch(DataIntegrityViolationException e){
            Assertions.assertNotNull(newUser);
        }
    }

    @Test
    @DisplayName("[mod.Security] Dao insert successfully work")
    void saveSuccessfullyWork(){
        //given
        Users newUser = MockUserBuilders.createDefaultUsers("thisIsNewUser");
        //when
        this.domainRepository.save(newUser);
        Users selected = this.domainRepository.findByUserIdWithException(newUser.getUserId());
        List<Users> listAll = this.userRepository.findAll();
        //then
        Assertions.assertNotNull(selected);
        Assertions.assertEquals(listAll.size(), 2);

        Assertions.assertEquals(selected.getUserId(), newUser.getUserId());
        Assertions.assertEquals(selected.getUserName(), newUser.getUserName());
        Assertions.assertEquals(selected.getPassword(), newUser.getPassword());
    }

    @Test
    @DisplayName("[mod.Security] Dao Update successfully work")
    void updateRefreshToken() {
        //given
        String newRefreshTokenValue = "I AM UPDATED!";
        Users selected = this.domainRepository.findByUserIdWithException(this.user.getUserId());
        //when
        selected.updateRefreshToken(newRefreshTokenValue);
        this.domainRepository.save(selected);
        Users afterChanged = this.domainRepository.findByUserIdWithException(this.user.getUserId());
        //then
        Assertions.assertNotNull(afterChanged);
        Assertions.assertEquals(afterChanged.getRefreshToken(), newRefreshTokenValue);
    }

    @Test
    @DisplayName("[mod.Security] Dao update or insert successfully work")
    void saveOrUpdateByUserId() {
        //given
        Users user = MockUserBuilders.createDefaultUsers("helloWorldNewUser");
        String newRefreshTokenValue = "I AM UPDATED!";
        //when
        this.user.updateRefreshToken(newRefreshTokenValue);
        Users updatedUser = this.domainRepository.saveOrUpdateByUserId(this.user);
        Users insertedUser = this.domainRepository.saveOrUpdateByUserId(user);
        List<Users> listAll = this.userRepository.findAll();
        //then
        Assertions.assertEquals(listAll.size(), 2);
        Assertions.assertEquals(updatedUser.getRefreshToken(), newRefreshTokenValue);
        Assertions.assertEquals(insertedUser.getUserId(), user.getUserId());
    }
}

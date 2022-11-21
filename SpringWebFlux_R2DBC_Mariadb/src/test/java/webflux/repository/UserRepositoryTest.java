package webflux.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.config.log.MarkersInfo;
import webflux.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllUser() {
        Long sizeUsers = userRepository.findAll().count().block();
        log.info(sizeUsers.toString());
    }

    @Test
    void findByEmailExits() {
        String emailExits = "trungitdevelop@gmail.com";
        userRepository.findByEmailExits(emailExits).map(user -> {
            log.info(user.toString());
            return user;
        }).switchIfEmpty(Mono.empty()).blockFirst();
    }

    @Test
    void saveOrUpdate() {
        // Create new object id = null
        UserEntity newUser = UserEntity.builder().password("admin").email("admin@gmail.com")
                .fullName("admin").branchId(1L).role(1).build();
        UserEntity userSaved = userRepository.saveOrUpdate(newUser).block();
        System.out.println(userSaved.toString());
        // Update object have id
        UserEntity userNeedUpdate = userRepository.findByEmailExits("admin@gmail.com").blockFirst();
        userNeedUpdate.setFullName("user");
        userNeedUpdate.setEmail("user@gmail.com");
        userNeedUpdate.setPassword("user");
        UserEntity userUpdated = userRepository.saveOrUpdate(userNeedUpdate).block();
        System.out.println(userUpdated.toString());
    }

    @Test
    void deletePhysical() {
        UserEntity userNeedDelete = userRepository.findByEmailExits("user@gmail.com").blockFirst();
        UserEntity userDeleted = userRepository.delete(userNeedDelete).block();
    }

    @Test
    void deleteLogic() {
        UserEntity userNeedDelete = userRepository.findByEmailExits("user@gmail.com").blockFirst();
        UserEntity userDeleted = userRepository.delete(userNeedDelete, "logic").block();
    }
}
package webflux.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webflux.dto.UserDTO;
import webflux.request.CreateUserRequest;
import webflux.service.UserService;

import java.util.List;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    private CreateUserRequest request;

    @BeforeEach
    void setUp() {
        request = CreateUserRequest.builder()
                .fullName("Request User")
                .email("request_user@gmail.com")
                .branchId(1L)
                .role(String.valueOf(1))
                .build();
    }

    @Test
    void saveUser() {
        Boolean result = userService.saveUser(request).block();
        System.out.println(result);
    }

    @Test
    void updateUser() {
        Boolean result = userService.updateUser(4L, request).block();
        System.out.println(result);
    }

    @Test
    void deleteUser() {
        Boolean result = userService.deleteUser(4L).block();
        System.out.println(result);
    }

    @Test
    void findUserDetail() {
        UserDTO result = userService.findUserDetail(5L).block();
        System.out.println(result);
    }

    @Test
    void findAllUser() {
        List<UserDTO> users = userService.findAllUser().block();
        System.out.println(users.size());
    }
}
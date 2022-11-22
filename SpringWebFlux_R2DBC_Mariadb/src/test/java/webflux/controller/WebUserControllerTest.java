package webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import webflux.request.CreateUserRequest;
import webflux.response.common.RootResponse;

@SpringBootTest
@Slf4j
class WebUserControllerTest {

    @Autowired
    WebUserController webUserController;

    private CreateUserRequest request;

    @BeforeEach
    void setUp() {
        request = CreateUserRequest.builder()
                .fullName("Request User")
                .email("request_user_update@gmail.com")
                .branchId(1L)
                .role(String.valueOf(1))
                .build();
    }

    @Test
    void findAllUser() {
        ResponseEntity<RootResponse> response = webUserController.findAllUser().block();
        log.info("Result: " + response.toString());
    }

    @Test
    void createUser() {
        ResponseEntity<RootResponse> result = webUserController.createUser(request).block();
        System.out.println(result);
    }

    @Test
    void updateUser() {
        ResponseEntity<RootResponse> result = webUserController.updateUser(1L, request).block();
        System.out.println(result);
    }

    @Test
    void deleteUser() {
        ResponseEntity<RootResponse> result = webUserController.deleteUser(1L).block();
        System.out.println(result);
    }
}
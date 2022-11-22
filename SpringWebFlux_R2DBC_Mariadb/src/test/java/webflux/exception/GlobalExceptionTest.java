package webflux.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import webflux.controller.WebUserController;
import webflux.response.common.RootResponse;

@SpringBootTest
class GlobalExceptionTest {

    @Autowired
    WebUserController webUserController;
    @Test
    void handleExceptionRuntime() {
        ResponseEntity<RootResponse> result = webUserController.deleteUser(4L).block();
        System.out.println(result);
    }

}
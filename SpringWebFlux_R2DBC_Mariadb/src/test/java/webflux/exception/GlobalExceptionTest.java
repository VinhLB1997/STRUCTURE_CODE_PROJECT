package webflux.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import webflux.exception.runtime.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GlobalExceptionTest {

    @Test
    void handleExceptionRuntime(){
//       throw new AccessDeniedException("Catch Exception Success");
    }

}
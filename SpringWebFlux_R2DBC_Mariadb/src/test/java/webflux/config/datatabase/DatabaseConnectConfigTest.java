package webflux.config.datatabase;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DatabaseConnectConfigTest {
    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp() {
    }

    @Test
    void connectionFactory() {
        ConnectionFactory factory = context.getBean("connectionFactory", ConnectionFactory.class);
        System.out.println(factory.getMetadata().toString());
    }
}
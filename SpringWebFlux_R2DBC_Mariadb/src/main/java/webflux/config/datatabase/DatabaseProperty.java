package webflux.config.datatabase;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class DatabaseProperty {

    @Value("${connection.r2dbc.select.host}")
    private String selectHost;

    @Value("${connection.r2dbc.save.host}")
    private String saveHost;

    @Value("${connection.r2dbc.username}")
    private String username;

    @Value("${connection.r2dbc.password}")
    private String password;

    @Value("${connection.r2dbc.database}")
    private String database;

    @Value("${connection.r2dbc.port}")
    private Integer port;

    @Value("${connection.r2dbc.pool.initial-size}")
    private Integer initSize;

    @Value("${connection.r2dbc.pool.max-size}")
    private Integer maxSize;

    @Value("${connection.r2dbc.pool.max-idle-time}")
    private Long idleTime;

    @Value("${connection.r2dbc.pool.max-life-time}")
    private Long lifeTime;
}

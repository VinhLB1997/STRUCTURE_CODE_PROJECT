package webflux.config.datatabase;

import java.time.Duration;

import io.r2dbc.proxy.ProxyConnectionFactory;
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter;
import lombok.extern.slf4j.Slf4j;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import webflux.config.WebConfig;
import webflux.config.log.MarkersInfo;

@Configuration
@Slf4j
public class DatabaseConnectConfig {

    private final DatabaseProperty databaseProperty;

    public DatabaseConnectConfig(DatabaseProperty databaseProperty) {
        this.databaseProperty = databaseProperty;
    }

    @Bean(name = "connectionFactory")
    @Profile(WebConfig.ENVIROMENT_PRODUCT)
    ConnectionFactory connectionFactory() {
        MariadbConnectionConfiguration conf = MariadbConnectionConfiguration.builder()
                .host(databaseProperty.getSaveHost()).port(databaseProperty.getPort())
                .username(databaseProperty.getUsername()).password(databaseProperty.getPassword())
                .database(databaseProperty.getDatabase()).build();
        ConnectionFactory connectionFactory = new MariadbConnectionFactory(conf);

        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .initialSize(databaseProperty.getInitSize()).maxSize(databaseProperty.getMaxSize())
                .maxIdleTime(Duration.ofMillis(databaseProperty.getIdleTime()))
                .maxLifeTime(Duration.ofMillis(databaseProperty.getLifeTime())).build();
        return new ConnectionPool(configuration);
    }

    @Bean(name = "connectionFactory")
    @Profile(WebConfig.ENVIROMENT_DEV)
    ConnectionFactory connectionFactoryDev() {
        MariadbConnectionConfiguration conf = MariadbConnectionConfiguration.builder().host(databaseProperty.getSaveHost())
                .port(databaseProperty.getPort()).username(databaseProperty.getUsername()).password(databaseProperty.getPassword())
                .database(databaseProperty.getDatabase()).build();
        ConnectionFactory connectionFactory = new MariadbConnectionFactory(conf);

        QueryExecutionInfoFormatter formatter = new QueryExecutionInfoFormatter().showQuery().newLine().showBindings()
                .newLine();
        ConnectionFactory proxyConnectionFactory = ProxyConnectionFactory.builder(connectionFactory)
                .onAfterQuery(queryInfo -> { // listener
                    log.info(MarkersInfo.SQL, formatter.format(queryInfo));
                }).build();

        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(proxyConnectionFactory)
                .initialSize(databaseProperty.getInitSize()).maxSize(databaseProperty.getMaxSize())
                .maxIdleTime(Duration.ofMillis(databaseProperty.getIdleTime()))
                .maxLifeTime(Duration.ofMillis(databaseProperty.getLifeTime())).build();
        ConnectionPool pool = new ConnectionPool(configuration);
        return pool;
    }

    @Bean(name = "entityTemplate")
    R2dbcEntityOperations saveEntityTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean(name = "transactionalOperator")
    ReactiveTransactionManager transactionalOperator(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean(name = "operator")
    TransactionalOperator transactionalOperator(ReactiveTransactionManager txm) {
        return TransactionalOperator.create(txm);
    }

}

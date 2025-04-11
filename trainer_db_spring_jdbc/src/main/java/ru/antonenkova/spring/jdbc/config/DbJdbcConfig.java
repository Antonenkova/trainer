package ru.antonenkova.spring.jdbc.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

@Configuration
public class DbJdbcConfig {
    @Value("${server}") private String server;
    @Value("${database}") private String database;
    @Value("${user}") private String user;
    @Value("${password}") private String password;

    @Bean
    @Lazy
    public DataSource dataSource() {
        try {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setServerName(server);
            ds.setDatabaseName(database);
            ds.setUser(user);
            ds.setPassword(password);
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
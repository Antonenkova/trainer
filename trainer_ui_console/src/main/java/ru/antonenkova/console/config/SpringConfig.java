package ru.antonenkova.console.config;

import org.springframework.context.annotation.*;
import ru.antonenkova.spring.jdbc.config.DbConfig;

@Configuration
@Import(DbConfig.class)
@ComponentScan(basePackages = "ru.antonenkova")
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {
}

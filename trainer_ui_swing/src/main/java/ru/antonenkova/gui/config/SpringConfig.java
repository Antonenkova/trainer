package ru.antonenkova.gui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.antonenkova.spring.hibernate.config.DbConfig;

@Configuration
@Import(DbConfig.class)
@ComponentScan(basePackages = "ru.antonenkova")
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {
}

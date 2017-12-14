package com.github.parkalot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("sqlite.properties")
public class SqliteConfig {

    @Value("${sqlite.url}")
    private String url;

    @Value("${sqlite.driver}")
    private String driver;

    @Bean("sqliteDS")
    DataSource dataSource() {
        final DriverManagerDataSource dmds = new DriverManagerDataSource();
        dmds.setUrl(url);
        dmds.setDriverClassName(driver);
        return dmds;
    }

    @Bean("sqliteTemplate")
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

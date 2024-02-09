package com.spring.batch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {
	
	@Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriverClassName;

    @Value("${spring.datasource.destination.url}")
    private String destinationDataSourceUrl;

    @Value("${spring.datasource.destination.username}")
    private String destinationDataSourceUsername;

    @Value("${spring.datasource.destination.password}")
    private String destinationDataSourcePassword;

    @Value("${spring.datasource.destination.driver-class-name}")
    private String destinationDataSourceDriverClassName;

    @Bean
    DataSource dataSource() {
        return DataSourceBuilder.create()
            .driverClassName(dataSourceDriverClassName)
            .url(dataSourceUrl)
            .username(dataSourceUsername)
            .password(dataSourcePassword)
            .build();
    }

    @Bean
    DataSource destinationDataSource() {
        return DataSourceBuilder.create()
            .driverClassName(destinationDataSourceDriverClassName)
            .url(destinationDataSourceUrl)
            .username(destinationDataSourceUsername)
            .password(destinationDataSourcePassword)
            .build();
    }
}

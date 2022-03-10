package com.hmfurtado.data.migration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {

//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasouce")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "testee.datasouce")
//    public DataSource dataSourceNovo() {
//        return DataSourceBuilder.create().build();
//    }
}

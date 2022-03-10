package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@EnableBatchProcessing
@Configuration
public class JobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dataSourceNewDB")
    private DataSource dataSource;

    @Autowired
    private WriteCustom writer;

    @Bean
    public Job fooJob() {
        return jobBuilderFactory.get("fooJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .<FooDto, FooDto>chunk(3)
                .reader(jdbcCursorItemReader())
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<FooDto> jdbcCursorItemReader() {
        JdbcCursorItemReader<FooDto> a = new JdbcCursorItemReader<FooDto>();
        a.setDataSource(dataSource);
        a.setSql("select * from title");
        a.setRowMapper(new BeanPropertyRowMapper<>(FooDto.class));

        return a;
    }


}

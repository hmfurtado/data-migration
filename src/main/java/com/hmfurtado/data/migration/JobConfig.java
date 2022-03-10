package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.annotation.processing.Processor;
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
    private DataSource dataSource;

    @Autowired
    private Writerzin writer;

    @Bean
    public Job jobzin() {
        return jobBuilderFactory.get("jobzin")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .<DTOzin, DTOzin>chunk(3)
                .reader(jdbcCursorItemReader())
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<DTOzin> jdbcCursorItemReader() {
        JdbcCursorItemReader<DTOzin> a = new JdbcCursorItemReader<DTOzin>();
        a.setDataSource(dataSource);
        a.setSql("select * from title");
        a.setRowMapper(new BeanPropertyRowMapper<>(DTOzin.class));

        return a;
    }


}

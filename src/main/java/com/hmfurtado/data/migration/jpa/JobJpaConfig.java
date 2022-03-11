package com.hmfurtado.data.migration.jpa;

import com.hmfurtado.data.migration.FooDto;
import com.hmfurtado.data.migration.FooDto2;
import com.hmfurtado.data.migration.ProcessorCustom;
import com.hmfurtado.data.migration.jpa.entitynew.NewEntity;
import com.hmfurtado.data.migration.jpa.entityold.OldEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collections;

@Slf4j
@EnableBatchProcessing
@Configuration
public class JobJpaConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dataSourceNewDB")
    private DataSource dataSourceNewDB;

    @Autowired
    @Qualifier("dataSourceLegacy")
    private DataSource dataSourceLegacy;

    @Autowired
    private JpaProcessor jpaProcessor;

    @Autowired
    @Qualifier("oldDatabaseEntityManagerFactory")
    private EntityManagerFactory oldDatabaseEntityManagerFactory;

    @Autowired
    @Qualifier("newDatabaseEntityManagerFactory")
    private EntityManagerFactory newDatabaseEntityManagerFactory;

    @Autowired
    private JpaListenerWriter jpaListenerWriter;

    @Autowired
    private JpaListenerReader jpaListenerReader;

    @Autowired
    private JpaTransactionManager jpaTransactionManager;

    @Bean
    public Job jpaJob() {
        return jobBuilderFactory.get("jpaJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .transactionManager(jpaTransactionManager)
                .<OldEntity, NewEntity>chunk(1000)
                .reader(jpaReader())
                .listener(jpaListenerReader)
                .processor(jpaProcessor)
                .writer(jpaWriter())
                .listener(jpaListenerWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader jpaReader() {
        return new JpaPagingItemReaderBuilder<OldEntity>()
                .name("jpaReader")
                .entityManagerFactory(oldDatabaseEntityManagerFactory)
                .queryString("select o from OldEntity o")
                .pageSize(1000)
                .build();
    }

    @Bean
    public JpaItemWriter<NewEntity> jpaWriter() {
        return new JpaItemWriterBuilder<NewEntity>()
                .entityManagerFactory(newDatabaseEntityManagerFactory).build();
    }

}

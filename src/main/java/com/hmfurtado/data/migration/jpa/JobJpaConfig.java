package com.hmfurtado.data.migration.jpa;

import com.hmfurtado.data.migration.FooDto2;
import com.hmfurtado.data.migration.jpa.entitynew.NewEntity;
import com.hmfurtado.data.migration.jpa.entityold.OldEntityWIthCompositePK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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
                .<OldEntityWIthCompositePK, NewEntity>chunk(3)
                .reader(jpaReader())
                .listener(jpaListenerReader)
                .processor(jpaProcessor)
                .writer(jpaWriter())
                .listener(jpaListenerWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader jpaReader() {
        return new JpaPagingItemReaderBuilder<OldEntityWIthCompositePK>()
                .name("jpaReader")
                .entityManagerFactory(oldDatabaseEntityManagerFactory)
                .queryString("select o from OldEntity o")
                .pageSize(10)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<FooDto2> jpaWriter() {
        return new JdbcBatchItemWriterBuilder<FooDto2>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO newtable3 " +
                        "(titleid, title, language) " +
                        "VALUES " +
                        "(:titleId, :title, :language)")
                .dataSource(dataSourceNewDB)
                .build();
    }

//    @Bean
//    public JpaItemWriter<NewEntity> jpaWriter() {
//        return new JpaItemWriterBuilder<NewEntity>()
//                .entityManagerFactory(newDatabaseEntityManagerFactory).build();
//    }

}

package com.hmfurtado.data.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;

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
    private DataSource dataSourceNewDB;

    @Autowired
    @Qualifier("dataSourceLegacy")
    private DataSource dataSourceLegacy;

    @Autowired
    private ReaderListenerCustom readerListenerCustom;

    @Autowired
    private WriterListenerCustom writerListenerCustom;

    @Autowired
    private ProcessorCustom processorCustom;

    @Bean
    public Job fooJob() {
        return jobBuilderFactory.get("fooJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end().build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .<FooDto, FooDto2>chunk(10)
                .reader(reader())
                .processor(processorCustom)
                .writer(writer())
                .build();
    }

//    WITHOUT PAGINATION, JUST FOR SMALL QUERIES
//    @Bean
//    public JdbcCursorItemReader<FooDto> reader() {
//        JdbcCursorItemReader<FooDto> a = new JdbcCursorItemReader<>();
//        a.setFetchSize(100);
//        a.setDataSource(dataSourceLegacy);
//        a.setSql("select * from title");
//        a.setRowMapper(new BeanPropertyRowMapper<>(FooDto.class));
//
//        return a;
//    }

    @Bean
    public ItemReader<FooDto> reader() {
        PostgresPagingQueryProvider provider = new PostgresPagingQueryProvider();
        provider.setSelectClause("select *");
        provider.setFromClause("from title");
        provider.setSortKeys(Collections.singletonMap("titleid", Order.ASCENDING));

        return new JdbcPagingItemReaderBuilder().name("readerPaging")
                .name("readerPaging")
                .dataSource(dataSourceLegacy)
                .fetchSize(1000)
                .pageSize(1000)
                .rowMapper(new BeanPropertyRowMapper<>(FooDto.class))
                .queryProvider(provider)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<FooDto2> writer() {
        return new JdbcBatchItemWriterBuilder<FooDto2>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO newtable3 " +
                        "(titleid, title, language) " +
                        "VALUES " +
                        "(:titleId, :title, :language)")
                .dataSource(dataSourceNewDB)
                .build();
    }

}

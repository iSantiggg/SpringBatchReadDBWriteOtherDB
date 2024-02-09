package com.spring.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.spring.batch.model.Empleado;

@Configuration
@EnableBatchProcessing
public class EmpleadoBatchConfiguration {

	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSource destinationDataSource;
    
    @Bean
    public JdbcCursorItemReader<Empleado> reader() {
        return new JdbcCursorItemReaderBuilder<Empleado>()
        	.name("empleadoReader")
            .dataSource(dataSource)
            .sql("SELECT * FROM csvtodbdata")
            .rowMapper(new BeanPropertyRowMapper<>(Empleado.class))
            .build();
    }
    
    @Bean
    public JdbcBatchItemWriter<Empleado> writer() {
        return new JdbcBatchItemWriterBuilder<Empleado>()
            .dataSource(destinationDataSource)
            .sql("INSERT INTO pruebas (id,nombre,apellido,email) VALUES (:id,:nombre,:apellido,:email)")
            .beanMapped()
            .build();
    }
    
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Empleado, Empleado>chunk(5)
                .reader(reader())
                .writer(writer())
                .build();
    }
    
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}

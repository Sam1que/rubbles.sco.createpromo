package ru.a366.sco_createpromo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.a366.sco_createpromo.common.db.DbService;

import javax.sql.DataSource;

@Slf4j
@Lazy
@Configuration
public class IntDbConfig {
    static JdbcTemplate jdbcTemplate(DataSource dataSource, JdbcProperties properties) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcProperties.Template template = properties.getTemplate();
        jdbcTemplate.setFetchSize(template.getFetchSize());
        jdbcTemplate.setMaxRows(template.getMaxRows());
        if (template.getQueryTimeout() != null) {
            jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
        }
        return jdbcTemplate;
    }

    @Bean
    @ConfigurationProperties("db.postgres.int")
    public DataSourceProperties intDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "db.postgres.int.hikari")
    public DataSource intDataSource(@Qualifier("intDataSourceProperties") DataSourceProperties intDataSourceProperties) {
        try {
            intDataSourceProperties.setPassword(intDataSourceProperties.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return intDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate intJdbcTemplate(@Qualifier("intDataSource") DataSource intDataSource) {
        return new JdbcTemplate(intDataSource);
    }

    @Bean
    public DbService intDbService(@Qualifier("intDataSource") DataSource intDataSource,
                                  @Qualifier("intJdbcTemplate") JdbcTemplate intJdbcTemplate, @Qualifier("DbObjectMapper")ObjectMapper objectMapper) {
        return new DbService(intDataSource, objectMapper, new NamedParameterJdbcTemplate(intJdbcTemplate));
    }
}

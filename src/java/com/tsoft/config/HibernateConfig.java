//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.config;

import com.tsoft.hibernate.interceptor.DefaultEntityInterceptor;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
    basePackages = {"com.tsoft"},
    excludeFilters = {@Filter(
    value = {Controller.class},
    type = FilterType.ANNOTATION
)}
)
@EnableTransactionManagement
@EnableScheduling
@PropertySource({"classpath:database.properties"})
public class HibernateConfig {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    @Resource
    private Environment env;

    public HibernateConfig() {
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.env.getRequiredProperty("db.driver"));
        dataSource.setUrl(this.env.getRequiredProperty("db.url"));
        dataSource.setUsername(this.env.getRequiredProperty("db.username"));
        dataSource.setPassword(this.env.getRequiredProperty("db.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(this.dataSource());
        return jdbcTemplate;
    }

    @Bean
    public SimpleJdbcCall simpleJdbcCall() {
        SimpleJdbcCall sjc = new SimpleJdbcCall(this.jdbcTemplate());
        return sjc;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(this.dataSource());
        sessionFactoryBean.setPackagesToScan(new String[]{this.env.getRequiredProperty("entitymanager.packages.to.scan")});
        sessionFactoryBean.setHibernateProperties(this.hibProperties());
        sessionFactoryBean.setEntityInterceptor(new DefaultEntityInterceptor());
        return sessionFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", this.env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", this.env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.enable_lazy_load_no_trans", true);
        if (this.env.getProperty("hibernate.hbm2ddl.auto") != null) {
            properties.put("hibernate.hbm2ddl.auto", this.env.getProperty("hibernate.hbm2ddl.auto"));
        }

        properties.put("hibernate.search.default.directory_provider", this.env.getRequiredProperty("hibernate.search.default.directory_provider"));
        properties.put("hibernate.search.default.indexBase", this.env.getRequiredProperty("hibernate.search.default.indexBase"));
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(this.sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

package com.example.demo.utils;



import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan( basePackages = {"com.example.demo.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@Slf4j
public class DatasourceConfig {

    @Value("${spring.datasource.my_db.jdbc-url}")
    private String url;

    @Value("${spring.datasource.my_db.username}")
    private String username;

    @Value("${spring.datasource.my_db.password}")
    private String password;

    @Value("${spring.datasource.my_db.driver-class-name}")
    private String driverClassName;

    @Bean("dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.mydb")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

}

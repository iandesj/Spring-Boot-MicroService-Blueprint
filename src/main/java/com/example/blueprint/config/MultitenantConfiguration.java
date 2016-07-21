package com.example.blueprint.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultitenantConfiguration {

    @Autowired
    private DataSourceProperties properties;

    /**
     * Defines the data source for the application
     * 
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        File[] files = Paths.get("tenants").toFile().listFiles();
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for(File propertyFile : files) {
            Properties tenantProperties = new Properties();
            DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

            try {
                tenantProperties.load(new FileInputStream(propertyFile));

                String tenantId = tenantProperties.getProperty("name");

                dataSourceBuilder.driverClassName(properties.getDriverClassName())
                        .url(tenantProperties.getProperty("spring.datasource.url"))
                        .username(tenantProperties.getProperty("spring.datasource.username"))
                        .password(tenantProperties.getProperty("spring.datasource.password"));

                if(properties.getType() != null) {
                    dataSourceBuilder.type(properties.getType());
                }

                resolvedDataSources.put(tenantId,dataSourceBuilder.build());
            }
            catch(IOException e) {
                e.printStackTrace();

                return null;
            }
        }

        // Create the final multi-tenant source.
        // It needs a default database to connect to.
        // Make sure that the default database is actually an empty tenant database.
        // Don't use that for a regular tenant if you want things to be safe!
        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setTargetDataSources(resolvedDataSources);

        // Call this to finalize the initialization of the data source.
        dataSource.afterPropertiesSet();

        return dataSource;
    }

    /**
     * Creates the default data source for the application
     * 
     * @return
     */
    private DataSource defaultDataSource() {
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
                .driverClassName(properties.getDriverClassName()).url(properties.getUrl())
                .username(properties.getUsername()).password(properties.getPassword());

        if(properties.getType() != null) {
            dataSourceBuilder.type(properties.getType());
        }

        return dataSourceBuilder.build();
    }
}
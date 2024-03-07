package com.lego.resource.config;

import com.lego.resource.entity.EntityArea;
import com.lego.resource.jpa.JpaRepositoryArea;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = EntityArea.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryArea.class)
@Import(DataSourceAutoConfiguration.class)
public class JpaConfig {
}

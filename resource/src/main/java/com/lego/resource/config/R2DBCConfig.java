package com.lego.resource.config;

import com.lego.resource.entity.EntityArea;
import com.lego.resource.j2dbc.R2DBCRepositoryArea;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EntityScan(basePackageClasses = EntityArea.class)
@EnableR2dbcRepositories(basePackageClasses = R2DBCRepositoryArea.class)
public class R2DBCConfig {
}

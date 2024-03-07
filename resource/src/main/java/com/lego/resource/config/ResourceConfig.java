package com.lego.resource.config;

import com.lego.infrastructure.config.InfraStructureConfig;
import com.lego.resource.j2dbc.R2DBCRepositoryArea;
import com.lego.resource.jpa.JpaRepositoryArea;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackageClasses = {
    InfraStructureConfig.class,
    JpaRepositoryArea.class,
    R2DBCRepositoryArea.class
  }
)
public class ResourceConfig {

}

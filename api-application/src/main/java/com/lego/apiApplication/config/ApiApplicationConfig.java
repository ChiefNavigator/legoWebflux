package com.lego.apiApplication.config;

import com.lego.apiApplication.restcontroller.RestControllerArea;
import com.lego.business.config.BusinessConfig;
import com.lego.infrastructure.config.InfraStructureConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackageClasses = {
    BusinessConfig.class,
    InfraStructureConfig.class,
    RestControllerArea.class
  }
)
public class ApiApplicationConfig {
}

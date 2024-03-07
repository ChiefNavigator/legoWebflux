package com.lego.business.config;

import com.lego.business.service.BusinessServiceArea;
import com.lego.domain.config.DomainConfig;
import com.lego.infrastructure.config.InfraStructureConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackageClasses = {
    DomainConfig.class,
    InfraStructureConfig.class,
    BusinessServiceArea.class
  }
)
public class BusinessConfig {
}

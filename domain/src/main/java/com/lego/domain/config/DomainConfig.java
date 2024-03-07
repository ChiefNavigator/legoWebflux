package com.lego.domain.config;

import com.lego.domain.manager.DomainManagerArea;
import com.lego.resource.config.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackageClasses = {
    DomainManagerArea.class,
    ResourceConfig.class
  }
)
public class DomainConfig {
}

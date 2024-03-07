package com.lego.infrastructure.config;

import com.lego.infrastructure.uiltity.UtilityArea;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
  basePackageClasses = {
    UtilityArea.class
  }
)
public class InfraStructureConfig {
}

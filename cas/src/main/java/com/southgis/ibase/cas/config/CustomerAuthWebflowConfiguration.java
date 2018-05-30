package com.southgis.ibase.cas.config;

import org.apereo.cas.config.CasWebflowContextConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * 注册配置
 *
 * @author simon
 * @create 2018-05-30 10:50
 **/
@Configuration("customerAuthWebflowConfiguration")
@EnableConfigurationProperties(value = CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomerAuthWebflowConfiguration {
  
  @Autowired
  @Qualifier("logoutFlowRegistry")
  private FlowDefinitionRegistry logoutFlowRegitry;
  
  @Autowired
  @Qualifier("loginFlowRegistry")
  private FlowDefinitionRegistry loginFlowRegistry;
  
  @Autowired
  @Qualifier("builder")
  private FlowBuilderServices builder;
  
  @Bean
  public CasWebflowConfigurer customWebflowConfigurer() {
    final CustomWebflowConfigurer customWebflowConfigurer = new CustomWebflowConfigurer(builder, loginFlowRegistry);
    customWebflowConfigurer.setLogoutFlowDefinitionRegistry(logoutFlowRegitry);
    return customWebflowConfigurer;
  }
}

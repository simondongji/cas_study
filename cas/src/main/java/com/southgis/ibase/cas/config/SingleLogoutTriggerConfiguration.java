package com.southgis.ibase.cas.config;

import com.southgis.ibase.cas.listen.TGTCreateEventListener;
import com.southgis.ibase.cas.service.TriggerLogoutService;
import com.southgis.ibase.cas.service.UserIdObtainServiceImpl;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单用户登出配置
 *
 * @author simon
 * @create 2018-05-30 15:20
 **/
@Configuration("singleLogoutTriggerConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class SingleLogoutTriggerConfiguration {
  @Autowired
  private CentralAuthenticationService centralAuthenticationService;
  
  /**
   * 触发登出服务
   *
   * @return 触发登出服务
   */
  @Bean
  protected TriggerLogoutService triggerLogoutService() {
    return new TriggerLogoutService(centralAuthenticationService);
  }
  
  @Bean
  //注册事件监听tgt的创建
  protected TGTCreateEventListener tgtCreateEventListener() {
    TGTCreateEventListener listener;
    listener = new TGTCreateEventListener(triggerLogoutService(), new UserIdObtainServiceImpl());
    return listener;
  }
}

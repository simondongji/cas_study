package com.southgis.ibase.cas.handler;

import com.southgis.ibase.cas.UsernamePasswordSysCredential;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.AccountNotFoundException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * 用户名系统认证，只要是admin用户加上sso系统就允许通过
 *
 * @author simon
 * @create 2018-05-30 10:34
 **/
public class UsernamePasswordSystemAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
  /**
   * 构造函数
   *
   * @param name
   * @param servicesManager
   * @param principalFactory
   * @param order
   */
  public UsernamePasswordSystemAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
    super(name, servicesManager, principalFactory, order);
  }
  
  @Override
  protected HandlerResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
    //当用户名为admin,并且system为sso即允许通过
    UsernamePasswordSysCredential sysCredential = (UsernamePasswordSysCredential) credential;
    if("admin".equals(sysCredential.getUsername()) && "sso".equals(sysCredential.getSystem())){
      //这里可以自定义属性数据
      return createHandlerResult(credential,this.principalFactory.createPrincipal(sysCredential.getUsername(),Collections.emptyMap()),null);
    }else{
      throw new AccountNotFoundException("必须是admin用户才容许通过");
    }
  }
  
  @Override
  public boolean supports(Credential credential) {
    return credential instanceof UsernamePasswordSysCredential;
  }
}

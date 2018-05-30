package com.southgis.ibase.cas;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.Size;

/**
 * 定义前端所需定义的绑定参数:用户，密码，部门
 *
 * @author simon
 * @create 2018-05-30 10:21
 **/
public class UsernamePasswordSysCredential extends RememberMeUsernamePasswordCredential {
  @Size(min = 2,message = "require system")
  private String system;
  
  public String getSystem() {
    return system;
  }
  
  public void setSystem(String system) {
    this.system = system;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.system).toHashCode();
  }
}

package com.southgis.ibase.casclient.auth;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;

/**
 * 过滤掉不需要跳转到登录界面鉴权的请求
 *
 * @author simon
 * @create 2018-05-28 16:40
 **/
public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
  /**
   * 判断请求是否符合规则,当还有/loginOut/success的字段，可以不登录
   *
   * @param url
   *        用户请求
   * @return
   *        true:不拦截，无需跳转登录界面
   *        false:拦截,跳转登录界面
   */
  @Override
  public boolean matches(String url) {
    return url.contains("/loginOut/success");
  }
  
  /**
   * 正则表达式的规则，这个地方可以是web传递过来的
   */
  @Override
  public void setPattern(String s) {
  
  }
}

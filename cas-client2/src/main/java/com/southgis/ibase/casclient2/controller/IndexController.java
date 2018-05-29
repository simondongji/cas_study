package com.southgis.ibase.casclient2.controller;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * 首页控制器
 *
 * @author simon
 * @create 2018-05-28 16:48
 **/
@RestController
public class IndexController {
  @RequestMapping("/index")
  public ModelAndView index(HttpServletRequest request) {
    //获取cas给我们传递回来的对象，这个东西放到了session中
    //session的 key是 _const_cas_assertion_
    Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);

    //获取登录用户名
    String loginName =assertion.getPrincipal().getName();
    System.out.printf("登录用户名:%s\r\n",loginName);

    //获取自定义返回值的数据
    Principal principal  = (AttributePrincipal) request.getUserPrincipal();
    if (request.getUserPrincipal() != null) {

      if (principal instanceof AttributePrincipal) {
        //cas传递过来的数据
        Map<String,Object> result =( (AttributePrincipal)principal).getAttributes();
        for(Map.Entry<String, Object> entry :result.entrySet()) {
          String key = entry.getKey();
          Object val = entry.getValue();
          System.out.printf("%s:%s\r\n",key,val);
        }
      }
    }
    return new ModelAndView("index");
  }
}

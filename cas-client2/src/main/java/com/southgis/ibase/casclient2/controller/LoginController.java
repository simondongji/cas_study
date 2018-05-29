package com.southgis.ibase.casclient2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 用户登出控制器
 *
 * @author simon
 * @create 2018-05-28 16:59
 **/
@RestController
@RequestMapping("/user")
public class LoginController {
  @RequestMapping("/loginOut1")
  public ModelAndView loginOut(HttpSession session) {
    session.invalidate();
    // http://passport.sso.com:8103/cas-client/user/loginOut1/success
    
    // 这个是直接退出，走的是默认退出方式
    return new ModelAndView("redirect:https://passport.sso.com:8100/cas/logout");
  }
  
  @RequestMapping("/loginOut2")
  public ModelAndView loginOut2(HttpSession session) {
    session.invalidate();
    // 退出登录后，跳转到退成成功的页面，不走默认页面
    return new ModelAndView("redirect:https://passport.sso.com:8100/cas/logout?service=http://passport.sso.com:8100:8103/user/loginOut/success");
  }
  
  @RequestMapping("/loginOut/success")
  @ResponseBody
  public String loginOut2() {
    return "注销成功";
  }
}

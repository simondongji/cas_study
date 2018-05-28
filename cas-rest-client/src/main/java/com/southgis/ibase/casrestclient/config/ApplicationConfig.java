/*
 * 版权所有.(c)2008-2017. 卡尔科技工作室
 */

package com.southgis.ibase.casrestclient.config;

import com.southgis.ibase.casrestclient.bean.SysUser;
import com.southgis.ibase.casrestclient.service.UserRepertory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public UserRepertory userRepertory() {
        SysUser admin = new SysUser().setUsername("rest-admin").setPassword("202cb962ac59075b964b07152d234b70").addAttribute("key", "keyVal");
        SysUser test = new SysUser().setUsername("rest-test").setPassword("202cb962ac59075b964b07152d234b70").addAttribute("test", "testVal");
        SysUser locked = new SysUser().setUsername("rest-locked").setPassword("202cb962ac59075b964b07152d234b70").setLocked(true);
        SysUser disable = new SysUser().setUsername("rest-disable").setPassword("202cb962ac59075b964b07152d234b70").setDisable(true);
        SysUser expired = new SysUser().setUsername("rest-expired").setPassword("202cb962ac59075b964b07152d234b70").setExpired(true);
        SysUser admin2 = new SysUser().setUsername("admin").setPassword("202cb962ac59075b964b07152d234b70").addAttribute("key", "keyVal");
        return new UserRepertory(admin, test, locked, disable, expired, admin2);
    }
}

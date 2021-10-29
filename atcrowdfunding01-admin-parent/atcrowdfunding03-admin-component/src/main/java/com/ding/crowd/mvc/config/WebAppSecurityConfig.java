package com.ding.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-28-19:59
 * @since JDK 1.8
 */

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
                .inMemoryAuthentication()
                .withUser("tom")
                .password(new BCryptPasswordEncoder().encode("123123"))
                .roles("ADMIN")
        ;*/

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // String数组，列出需要放行的资源的路径
        String[] permitUrls = {"/index.jsp", "/bootstrap/**",
                "/crowd/**", "/css/**", "/fonts/**", "/img/**",
                "/jquery/**", "/layer/**", "/script/**", "/ztree/**", "/admin/do/login.html"};
        http
                .authorizeRequests()                     // 表示对请求进行授权
                .antMatchers(permitUrls)                 // 传入的ant风格的url
                .permitAll()                             // 允许上面的所有请求，不需要认证
                .antMatchers("/admin/get/page.html")  // 针对分页显示Admin数据设定访问控制
                .hasRole("经理")                              // 要求具备经理角色
                .anyRequest()                             // 设置其他未设置的全部请求
                .authenticated()                          // 表示需要认证
                .and()
                .formLogin()                               // 开启表单的身份验证
                .loginPage("/admin/do/login.html")            // 登录页设置
                .loginProcessingUrl("/security/do/login.html")   // 验证请求的地址
                .defaultSuccessUrl("/admin/to/main.html")        // 验证成功后的地址
                .permitAll()
                .usernameParameter("userAcct")      // 帐号的请求参数名
                .passwordParameter("userPswd")      // 密码的请求参数名
                .and()
                .csrf()
                .disable()
                .logout()                           // 开启退出登录
                .logoutRequestMatcher(new AntPathRequestMatcher("/security/do/logout.html", "GET")) // 设置退出地址
                .logoutSuccessUrl("/admin/do/login.html")                 // 设置退出成功后去那
        ;
    }
}

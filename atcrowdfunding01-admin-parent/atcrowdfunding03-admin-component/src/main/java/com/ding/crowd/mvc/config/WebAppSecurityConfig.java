package com.ding.crowd.mvc.config;

import com.ding.crowd.constant.CrowdContant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-28-19:59
 * @since JDK 1.8
 */


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

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
              /*  .antMatchers("/admin/get/page.html")  // 针对分页显示Admin数据设定访问控制
                .hasRole("经理")                              // 要求具备经理角色*/
                .anyRequest()                             // 设置其他未设置的全部请求
                .authenticated()                          // 表示需要认证
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception",new Exception("抱歉，您没有权限访问该资源！"));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                    }
                })
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

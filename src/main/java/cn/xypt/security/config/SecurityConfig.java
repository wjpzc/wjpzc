package cn.xypt.security.config;

import cn.xypt.security.filter.JsonUsernamePasswordAuthenticationFilter;
import cn.xypt.security.filter.JwtAuthenticationFilter;
import cn.xypt.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)


public class SecurityConfig {

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;




    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //1.放行部分请求
        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeHttpRequests(request ->{
//            request.requestMatchers("/user/login"
////                    "/user/info",
////                    "/user/logout"
//            ).anonymous()
//                    .anyRequest().authenticated();
//        });
        //2.登录配置
//        http.formLogin(form -> {
//            form.loginProcessingUrl("/user/login")
//                    .successHandler(new AuthenticationSuccessHandler(){
//                        @Override
//                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                            response.getWriter().write("success");
//
//                        }
//                    });
//        });
        //3.前后端分离，session
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        //4.禁用csrf
        http.csrf(csrf -> {
            csrf.disable();
        });

        //注销
        http.logout(logout -> {
            logout.logoutUrl("/user/logout");
            logout.logoutSuccessHandler(myLogoutSuccessHandler);
        });

        //未登录处理
        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(myAuthenticationEntryPoint)   //未登录处理
                    .accessDeniedHandler(myAccessDeniedHandler);    //无权限处理
        });

        //注册JsonUsernamePasswordAuthenticationFilter
        http.addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        //注册jwtAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        filter.setFilterProcessesUrl("/user/login");
        //认证成功
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        //认证失败
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return filter;
    }

}

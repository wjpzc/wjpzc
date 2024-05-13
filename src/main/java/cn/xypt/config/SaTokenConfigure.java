package cn.xypt.config;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * Sa-Token 整合 jwt (Stateless 无状态模式)
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }


    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
//        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
//            .addPathPatterns("/**")
//            .excludePathPatterns("/user/login", "/doc.html", "/webjars/**", "/swagger-resources",
//                "/swagger-resources/configuration/ui", "/v2/api-docs", "/v2/api-docs-ext",
//                "/favicon.ico");
    }
}

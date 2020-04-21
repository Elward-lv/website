package com.website.springboot.configuration;

import com.website.springboot.component.loginHandleInterrupt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 这里可以配置web MVC的设置，如注册过滤器，拦截器，或者静态资源管理器等其他类型的组件
 */
@Configuration
public class WebMvcConfig  extends WebMvcConfigurationSupport {
    /**
     * 拦截器，防止未登录的人员操作一些东西
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new loginHandleInterrupt()).addPathPatterns("/**").excludePathPatterns("/user/register","/user/login","/static/**");
        super.addInterceptors(registry);
    }

    /**
     * 支持跨域,和前端交互
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")//需要设置固定
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
        super.addCorsMappings(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/").addResourceLocations("classpath:/static/dist/index.html");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/dist/favicon.ico");
        registry.addResourceHandler("/dist/**").addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/dist/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/dist/img/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        super.addViewControllers(registry);
    }
}
